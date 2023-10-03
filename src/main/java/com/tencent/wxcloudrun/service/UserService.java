package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.*;
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


}
