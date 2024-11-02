package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cssa.wxcloudrun.dao.RideMapper;
import org.cssa.wxcloudrun.model.Contact;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.Ride;
import org.cssa.wxcloudrun.service.RideService;
import org.jetbrains.annotations.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 获取未被移除的顺风车列表。
     *
     * @param offset 从结果集开始的偏移量
     * @param limit  每页结果的最大数量
     * @return 包含未被移除的顺风车列表的响应
     */
    @Override
    public Response<List<Ride>> getRideList(Integer offset, Integer limit) {
        List<Ride> rideList = rideMapper.getRideList(offset, limit);
        Timestamp currentTime = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("America/Chicago")).toLocalDateTime());

        List<Ride> returnedList = new ArrayList<>();

        rideList.forEach(ride -> {
            boolean isExpired = checkIfExpired(ride, currentTime);

            if (isExpired) {
                // 更新数据库，将其标记为已移除
                ride.setRemovedTime(currentTime);
                removeRide(ride.getRideId());
            } else {
                // 只为未过期的顺风车解析JSON数据到Contact和images列表
                ride.setContactInfo(JSON.parseObject(ride.getContactInfoJSON(), Contact.class));
                ride.setImages(JSON.parseArray(ride.getImagesJSON(), String.class));

                returnedList.add(ride);
            }
        });

        return new Response<>(returnedList);
    }

    /**
     * 获取被移除的顺风车列表。
     *
     * @param userId 用户的微信 openID，作为用户的唯一标识符
     * @param offset 从结果集开始的偏移量
     * @param limit  每页结果的最大数量
     * @return 包含被移除顺风车列表的响应
     */
    @Override
    public Response<List<Ride>> getRemovedRideList(String userId, Integer offset, Integer limit) {
        List<Ride> rideList = rideMapper.getRemovedRideList(userId,offset,limit);
        rideList.forEach(ride -> {
            ride.setContactInfo(JSON.parseObject(ride.getContactInfoJSON(), Contact.class));
            ride.setImages(JSON.parseArray(ride.getImagesJSON(),String.class));
        });
        return new Response<>(rideList);
    }

    /**
     * 检查指定用户是否拥有特定的顺风车。
     *
     * 该方法通过用户的微信 openID 和顺风车的 rideID 检查该顺风车是否属于该用户。
     *
     * @param userId 用户的微信 openID，作为用户的唯一标识符
     * @param rideID 顺风车的唯一标识符
     * @return 如果该顺风车属于该用户，则返回 true；否则返回 false
     */
    @Override
    public boolean isRideOwnedByUser(String userId, Integer rideID) {
        return rideMapper.isRideOwnedByUser(userId, rideID);
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
     * 移除顺风车(软删除顺风车信息)。
     * 该方法会将顺风车信息标记为不可见，但仍然保留在数据库中。
     * 用户仍然可以看到或编辑该顺风车信息，但不会向公众展示。
     *
     * @param rideID 要移除的顺风车信息的Id
     * @return 返回移除操作是否成功
     */
    @Override
    public boolean removeRide(Integer rideID) {
        return rideMapper.removeRide(rideID);
    }

    /**
     * 彻底删除顺风车信息 (删除顺风车)。
     * 该方法会从数据库中完全删除顺风车信息，数据将不可恢复。
     *
     * @param rideId 要删除的顺风车信息的Id
     * @return 返回删除操作是否成功
     */
    @Override
    public boolean deleteRide(Integer rideId) {
        return rideMapper.deleteRide(rideId);
    }

    /**
     * 查看顺风车信息是否已发布
     *
     * @param rideID 顺风车信息的Id
     * @return 顺风车信息是否被发布
     */
    @ApiStatus.Internal
    public boolean isPublished(Integer rideID) {
        return rideMapper.isPublished(rideID);
    }

    /**
     * 检查顺风车是否过期
     */
    @ApiStatus.Internal
    private boolean checkIfExpired(Ride ride, Timestamp currentTime) {
        System.out.println("checkIfExpired is called.");
        if (ride.getDepartureTime() == null) {
            // 如果没有出发时间，无法判断是否过期，直接返回 false
            return false;
        }

        // 单程的情况，只需要检查出发时间是否过期
        if (ride.getRideType() == 1) {
            return ride.getDepartureTime().before(currentTime);
        }

        // 往返的情况，需要检查出发时间和返回时间是否过期
        if (ride.getRideType() == 2) {
            return ride.getDepartureTime().before(currentTime) &&
                    (ride.getReturnTime() != null && ride.getReturnTime().before(currentTime));
        }

        // 默认情况，返回 false
        return false;
    }
}
