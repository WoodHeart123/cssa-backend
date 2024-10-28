package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.*;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

@Service
public interface UserService {
    /**
     * 向用户邮箱发送验证码
     *
     * @param email    用户邮箱
     * @param authCode 验证码
     */
    void getAuthCode(String email, Integer authCode);

    /**
     * 验证成功，在数据修改身份
     *
     * @param userID 微信 open id
     */
    Response<Object> authSuccess(String userID);

    /**
     * 用户登录。如第一次登录，将会设置用户昵称
     *
     * @param nickname 用户昵称
     * @param userID   微信 openid
     */
    Response<Object> login(String nickname, String userID) throws UnsupportedEncodingException;

    /**
     * 更新用户邮箱
     *
     * @param email     邮箱
     * @param subscribe 是否接受广告
     * @param userID    微信 openid
     */
    Response<Object> updateEmail(String email, Boolean subscribe, String userID);

    /**
     * 更新头像
     *
     * @param userID 微信 openid
     * @param avatar 头像id
     */
    Response<Object> updateAvatar(String userID, Integer avatar);

    Response<List<CourseComment>> getMyComment(String userID, Integer offset, Integer limit);

    Response<Object> updateComment(String userID, Integer commentID, String comment);

    Response<Object> deleteComment(String userID, Integer commentID);

    /**
     * @param rentalID 唯一转租ID
     * @param userID   微信 openid
     * @param time     时间
     */
    Response<Object> setRentalTime(Integer rentalID, String userID, Timestamp time);

    /**
     * 获取对应用户id的转租
     *
     * @param userID 微信 openid
     * @param offset id偏移
     * @param limit  限制条数
     */
    Response<List<Rental>> getMyRental(String userID, Integer offset, Integer limit);

    Response<Object> updateWechatID(String wechatID, String userID);

    Response<Object> updateNickname(String userID, String nickname);

    Response<List<Product>> getMySecondhand(String userID, Integer offset, Integer limit);

    Response<Object> updateMySecondHand(String userID, Product product);

    Response<Object> deleteMySecondHand(String userID, Integer productID);

    /**
     * 删除转租
     *
     * @param userID   微信 openid
     * @param rentalID 唯一转租id
     */
    Response<Object> deleteMyRental(String userID, Integer rentalID);

    Response<Object> setProductTime(Integer productID, String userID, Timestamp time);

    /**
     * 订阅用户的邮件地址。
     *
     * 该方法更新数据库中用户的邮箱地址，并将用户的订阅状态设置为已订阅。
     * 然后发布一个订阅事件。
     *
     * @param subscription 包含用户订阅信息的对象，其中包括用户的邮箱地址和 openID。
     * @return 返回一个包含订阅操作结果的响应对象。如果操作成功，返回 `Response<Boolean>` 包含 `true`。
     */
    Response<Boolean> subscribe(Subscription subscription);

    /**
     * 取消用户的邮件订阅。
     *
     * 该方法根据用户的 openID 从数据库中获取用户的邮箱地址，并将用户的订阅状态设置为未订阅。
     * 然后发布一个取消订阅事件。
     *
     * @param openID 要取消订阅的用户的唯一 openId.
     * @return 返回一个包含取消订阅操作结果的响应对象。如果操作成功且用户存在，返回 `Response<Boolean>` 包含 `true`；否则返回 `false`。
     */
    Response<Boolean> unsubscribe(String openID);

    /**
     * 检查用户的邮件订阅状态。
     *
     * 该方法根据用户的 openID 从数据库中获取用户的订阅状态并返回。默认数据表中已有openID的相关数据。
     *
     * @param openID 微信用户在小程序的唯一标识符。
     * @return 用户是否订阅了邮件服务。
     */
    Response<Boolean> isSubscribed(String openID);

    /**
     * 检查用户是否被拉黑
     *
     * 该方法根据用户的 openID 从数据库中获取用户的被拉黑状态并返回。默认值为0-否。
     *
     * @param openID 微信用户在小程序的唯一标识符。
     * @return 用户是否被拉黑。1-是；0-否。
     */
    Response<Boolean> isBlocked(String openID);

    /**
     * 更新指定用户的联系方式。
     * 该方法根据用户ID更新用户的联系信息，包括电话号码、微信ID和电子邮件地址。
     * 如果传入的联系信息字段为null或空字符串，则不会更新相应字段，保留数据库中的原有值。
     *
     * @param userId 用户的唯一标识符（ID）。
     * @param info   包含用户联系信息的对象，包括电话号码（phoneNumber）、微信ID（weChatId）和电子邮件地址（email）。
     */
    void saveContact(String userId, Contact info);

}
