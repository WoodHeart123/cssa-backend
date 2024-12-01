package org.cssa.wxcloudrun.service;
import org.cssa.wxcloudrun.model.Ride;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RideService {
    /**
     * 获取特定顺风车信息。
     *
     * @param rideId 顺风车的唯一标识符
     * @return 包含指定顺风车信息的响应
     */
    Response<Ride> getRide(Integer rideId);

    /**
     * 获取未被移除的顺风车列表。根据发布时间排序。
     *
     * @param offset 从结果集开始的偏移量
     * @param limit 每页结果的最大数量
     * @return 包含未被移除顺风车列表的响应
     */
    Response<List<Ride>> getRideList(Integer offset, Integer limit);

    /**
     * 获取该用户被移除的顺风车列表。
     *
     * @param userId 用户的微信 openID，作为用户的唯一标识符
     * @param offset 从结果集开始的偏移量
     * @param limit 每页结果的最大数量
     * @return 包含被移除顺风车列表的响应
     */
    Response<List<Ride>> getRemovedRideList(String userId,Integer offset, Integer limit);

    /**
     * 检查指定用户是否拥有特定的顺风车。
     *
     * 该方法通过用户的微信 openID 和顺风车的 rideID 检查该顺风车是否属于该用户。
     *
     * @param userId 用户的微信 openID，作为用户的唯一标识符
     * @param rideId 顺风车的唯一标识符
     * @return 如果该顺风车属于该用户，则返回 true；否则返回 false
     */
    boolean isRideOwnedByUser(String userId, Integer rideId);

    /**
     * 保存顺风车信息。
     * 该方法用于发布新的顺风车信息，保存到数据库中。
     *
     * @param ride 要保存的顺风车信息
     * @return 返回保存成功与否的布尔值
     */
    boolean publishRide(Ride ride);

    /**
     * 更新顺风车信息。
     * 用户可以通过该方法更新顺风车信息。
     * 如果 ifPublish 参数为 true，则顺风车将被发布；如果为 false 或未提供，则顺风车仅更新而不发布。
     *
     * @param userId 用户的微信 openID
     * @param ride 更新后的顺风车信息
     * @param ifPublish 是否发布顺风车，默认值为 false
     * @return 返回更新操作是否成功
     */
    boolean updateRide(String userId, Ride ride, boolean ifPublish);

    /**
     * 移除顺风车(软删除顺风车信息)。
     * 该方法会将顺风车信息标记为不可见，但仍然保留在数据库中。
     * 用户仍然可以看到或编辑该顺风车信息，但不会向公众展示。
     *
     * @param rideId 要移除的顺风车信息的Id
     * @return 返回移除操作是否成功
     */
    boolean removeRide(Integer rideId);

    /**
     * 彻底删除顺风车信息 (删除顺风车)。
     * 该方法会从数据库中完全删除顺风车信息，数据将不可恢复。
     *
     * @param rideId 要删除的顺风车信息的Id
     * @return 返回删除操作是否成功
     */
    boolean deleteRide(Integer rideId);
}
