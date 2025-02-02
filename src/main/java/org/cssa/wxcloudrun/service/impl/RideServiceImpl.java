package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cssa.wxcloudrun.dao.RideMapper;
import org.cssa.wxcloudrun.model.Contact;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.Ride;
import org.cssa.wxcloudrun.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@Service
public class RideServiceImpl implements RideService {
    @Autowired
    RideMapper rideMapper;

    /**
     * 获取特定顺风车信息。
     *
     * @param rideId 顺风车的唯一标识符
     * @return 包含指定顺风车信息的响应
     */
    @Override
    public Response<Ride> getRide(Integer rideId) {
        Ride ride = rideMapper.getRide(rideId);
        ride.setContactInfo(JSON.parseObject(ride.getContactInfoJSON(), Contact.class));
        ride.setImages(JSON.parseArray(ride.getImagesJSON(), String.class));
        return new Response<>(ride);
    }

    /**
     * 获取未被移除的顺风车列表。根据发布时间排序。
     *
     * @param offset 从结果集开始的偏移量
     * @param limit  每页结果的最大数量
     * @param openId （可选）用于筛选指定用户的顺风车列表。如果为 null，则不筛选用户。
     * @return 包含未被移除的顺风车列表的响应
     */
    @Override
    public Response<List<Ride>> getRideList(Integer offset, Integer limit, @Nullable String openId) {
        int batchSize = limit;
        List<Ride> returnedList  = new ArrayList<>();
        Timestamp currentCSTTime = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("America/Chicago")).toLocalDateTime());

        // 检查返回的顺风车中是否由未被标记但已过期的顺风车信息
        while (returnedList.size() < limit) {
            List<Ride> rideList;
            if (openId == null) {
                rideList = rideMapper.getRideList(offset, batchSize);
            } else {
                rideList = rideMapper.getRideListByUserId(openId, offset, batchSize);
            }

            // 数据源耗尽，退出循环
            if (rideList.isEmpty()) {
                break;
            }

            // 遍历当前获取的顺风车列表
            for (Ride ride : rideList) {
                // 与当前CST时间对比检查顺风车是否过期。
                boolean isExpired = checkIfExpired(ride, currentCSTTime);

                if (isExpired) {
                    // 将过期顺风车隐藏
                    hideRide(ride.getRideId());
                } else if (ride.getPublishedTime() != null) {
                    // 将有效顺风车的 JSON 数据解析到 Contact 和 images 列表
                    ride.setContactInfo(JSON.parseObject(ride.getContactInfoJSON(), Contact.class));
                    ride.setImages(JSON.parseArray(ride.getImagesJSON(), String.class));

                    // 处理好的顺风车数据添加到返回列表中
                    returnedList.add(ride);

                    // 如果返回列表已满足需求数量，退出内层循环
                    if (returnedList.size() >= limit) {
                        break;
                    }
                }
            }

            // 调整偏移量，继续获取下一批顺风车
            offset += batchSize;
        }

        // 根据 publishedTime 对返回的顺风车列表进行降序排序
        returnedList.sort((ride1, ride2) -> ride2.getPublishedTime().compareTo(ride1.getPublishedTime()));

        return new Response<>(returnedList);
    }

    /**
     * 检查顺风车是否过期
     */
    private boolean checkIfExpired(Ride ride, Timestamp currentTime) {
        if (ride.getDepartureTime() == null) {
            // 如果没有出发时间，无法判断是否过期，直接返回 false
            return false;
        }

        // 单程的情况，只需要检查出发时间是否过期
        if (ride.getRideType() == 0) {
            return ride.getDepartureTime().before(currentTime);
        }

        // 往返的情况，需要检查出发时间和返回时间是否过期
        if (ride.getRideType() == 1) {
            return ride.getDepartureTime().before(currentTime) &&
                    (ride.getReturnTime() != null && ride.getReturnTime().before(currentTime));
        }

        // 默认情况，返回 false
        return false;
    }

    /**
     * 获取该用户被隐藏（下架）的顺风车列表。
     *
     * @param openId 用户的微信 openID，作为用户的唯一标识符
     * @param offset 从结果集开始的偏移量
     * @param limit 每页结果的最大数量
     * @return 包含被隐藏（下架）顺风车列表的响应
     */
    @Override
    public Response<List<Ride>> getHiddenRideList(String openId, Integer offset, Integer limit) {
        List<Ride> rideList = rideMapper.getHiddenRideList(openId,offset,limit);
        rideList.forEach(ride -> {
            // 将有效顺风车的 JSON 数据解析到 Contact 和 images 列表
            ride.setContactInfo(JSON.parseObject(ride.getContactInfoJSON(), Contact.class));
            ride.setImages(JSON.parseArray(ride.getImagesJSON(), String.class));
        });
        return new Response<>(rideList);
    }

    /**
     * 检查指定用户是否拥有特定的顺风车。
     *
     * 该方法通过用户的微信 openID 和顺风车的 rideID 检查该顺风车是否属于该用户。
     *
     * @param openId 用户的微信 openID，作为用户的唯一标识符
     * @param rideID 顺风车的唯一标识符
     * @return 如果该顺风车属于该用户，则返回 true；否则返回 false
     */
    @Override
    public boolean isRideOwnedByUser(String openId, Integer rideID) {
        return rideMapper.isRideOwnedByUser(openId, rideID);
    }

    /**
     * 保存顺风车信息。
     * 该方法用于发布新的顺风车信息，保存到数据库中。
     *
     * @param ride 要保存的顺风车信息
     * @return 返回保存成功与否的布尔值
     */
    @Override
    public boolean publishRide(Ride ride) {
        ride.setContactInfoJSON(JSON.toJSONString(ride.getContactInfo()));
        ride.setImagesJSON(JSON.toJSONString(ride.getImages()));
        return rideMapper.publishRide(ride);
    }

    /**
     * 更新顺风车信息。
     * 用户可以通过该方法更新顺风车信息。
     * 如果 ifPublish 参数为 true，则顺风车将被发布；如果为 false 或未提供，则顺风车仅更新而不发布。
     *
     * @param userId 用户的微信 openID
     * @param ride   更新后的顺风车信息
     * @param ifToPublish 是否发布顺风车，默认值为 false
     * @return 返回更新操作是否成功
     */
    @Override
    public boolean updateRide(String userId, Ride ride, boolean ifToPublish) {
        ride.setContactInfoJSON(JSON.toJSONString(ride.getContactInfo()));
        ride.setImagesJSON(JSON.toJSONString(ride.getImages()));
        return rideMapper.updateRide(userId, ride, ifToPublish);
    }


    /**
     * 移除顺风车。
     * 用户仍然可以看到或编辑该顺风车信息，但不会向公众展示。
     * 用户无法再看到该顺风车信息。
     *
     * @param rideId 要移除的顺风车信息的Id
     * @return 返回移除操作是否成功
     */
    @Override
    public boolean removeRide(Integer rideId) {
        return rideMapper.removeRide(rideId);
    }

    /**
     * 隐藏（下架）顺风车。
     *
     * 该方法会将顺风车信息标记为“已过期”保留在数据库中。
     * 用户仍然可以看到或编辑该顺风车信息。
     *
     * @param rideId 要移除的顺风车信息的Id
     * @return 返回移除操作是否成功
     */
    @Override
    public boolean hideRide(Integer rideId) {
        return rideMapper.hideRide(rideId);
    }

    /**
     * 查看顺风车信息是否已发布
     *
     * @param rideID 顺风车信息的Id
     * @return 顺风车信息是否被发布
     */

    public boolean isPublished(Integer rideID) {
        return rideMapper.isPublished(rideID);
    }
}
