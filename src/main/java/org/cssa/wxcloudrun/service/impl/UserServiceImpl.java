package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.event.AuthEvent;
import org.cssa.wxcloudrun.event.SubscriptionEvent;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.UserService;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserMapper userMapper;

    @Autowired
    EncryptionUtil encryptionUtil;

    @Override
    public void getAuthCode(String email, Integer authCode) {
        applicationContext.publishEvent(new AuthEvent(this, email, authCode));
    }


    @Override
    public Response<Object> wechatLogin(String nickname, String openID){
        User user = userMapper.getUserByOpenID(openID);
        if (user == null) {
            user = userMapper.createUser(nickname);
            user.setOpenID(openID);
            userMapper.updateUser(user);
            return Response.builder().data(user).status(103).message("新用户").build();
        }
        return new Response<>(user);
    }

    public Response<Object> updateUser(User user) {
        userMapper.updateUser(user);
        return new Response<>();
    }

    @Override
    public Response<Object> setRentalTime(Integer rentalID, Integer userID, Timestamp time) {
        if (time.equals(new Timestamp(0))) {
            userMapper.clearRentalTime(rentalID, userID);
        } else {
            userMapper.setRentalTime(rentalID, userID, time);
        }
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> setProductTime(Integer productID, Integer userID, Timestamp time) {
        if (time.equals(new Timestamp(0))) {
            userMapper.clearProductTime(productID, userID);
        } else {
            userMapper.setProductTime(productID, userID, time);
        }
        return new Response<>();
    }

//    @Override
//    public Response<Boolean> subscribe(Subscription subscription) {
//        String openID = subscription.getOpenID(), email = subscription.getEmail();
//        if (openID.isBlank() || email.isBlank()) return new Response<>(Boolean.FALSE);
//
//        userMapper.updateEmail(email, true, openID);
//
//        String encryptedID = makeAnEncryptedIdforUser(openID);
//        userMapper.updateEncryptedID(openID,encryptedID);
//        subscription.setOpenID(encryptedID); // 使用加密ID发布事件
//
//        applicationContext.publishEvent(new SubscriptionEvent(this, subscription, true));
//        return new Response<>(Boolean.TRUE);
//    }
//
//    @Override
//    public Response<Boolean> unsubscribe(String openID) {
//        String email = userMapper.getEmail(openID);
//        if (email.isBlank()) return new Response<>(Boolean.FALSE);
//
//        userMapper.updateEmail(email, false, openID);
//
//        SubscriptionEvent event = new SubscriptionEvent(this, new Subscription(openID, email), false);
//        applicationContext.publishEvent(event);
//        return new Response<>(Boolean.TRUE);
//    }
//
//    /**
//     * 为用户生成一个加密的ID。
//     * 该方法使用用户的openID和当前时间戳生成一个加密ID，确保该ID在数据库中是唯一的。
//     * 如果生成的加密ID已存在，则重新生成，直到产生一个唯一的加密ID。
//     * 此方法不在UserService中。
//     *
//     * @param openID 用户的OpenID，用作生成加密ID的基础之一。
//     * @return 返回生成的唯一加密ID。
//     */
//    public String makeAnEncryptedIdforUser(String openID) {
//        boolean encryptedIDExists;
//        String encryptedID;
//        do {
//            encryptedID = encryptionUtil.generateEncryptedID(openID, System.currentTimeMillis());
//            encryptedIDExists = userMapper.ifEncryptedIDExists(encryptedID);
//        } while (encryptedIDExists);
//        return encryptedID;
//    }
//
//    @Override
//    public Response<Boolean> isSubscribed(String openID) {
//        return new Response<>(userMapper.isSubscribed(openID));
//    }
//
//    /**
//     * 检查用户是否被拉黑
//     *
//     * 该方法根据用户的 openID 从数据库中获取用户的被拉黑状态并返回。默认值为0-否。
//     *
//     * @param openID 微信用户在小程序的唯一标识符。
//     * @return 用户是否被拉黑。true-是；false-否。
//     */
//    @Override
//    public Response<Boolean> isBlocked(String openID) {return new Response<>(userMapper.isBlocked(openID));}
//
//    /**
//     * 更新指定用户的联系方式。
//     * 该方法根据用户ID更新用户的联系信息，包括电话号码、微信ID和电子邮件地址。
//     * 如果传入的联系信息字段为null或空字符串，则不会更新相应字段，保留数据库中的原有值。
//     *
//     * @param userId 用户的唯一标识符（ID）。
//     * @param info   包含用户联系信息的对象，包括电话号码（phoneNumber）、微信ID（weChatId）和电子邮件地址（email）。
//     */
//    @Override
//    public void saveContact(String userId, Contact info) { userMapper.saveContact(userId, info);}
//
//    /**
//     * 保存用户目前使用的微信昵称和头像到库中。
//     *
//     * @param userId    用户在小程序的唯一标识符（ID）。
//     * @param nickName  用户目前使用的昵称。
//     * @param avatarUrl 用户目前使用的头像链接。它指向微信服务器上的头像图片资源。
//     * @return
//     */
//    @Override
//    public Response<Boolean> saveUserInfo(String userId, String nickName, String avatarUrl) {
//        return new Response<>(userMapper.saveUserInfo(userId,nickName,avatarUrl));
//    }
//
//    /**
//     * 从库中获取保存的用户使用的微信昵称和头像。
//     *
//     * @param userId 用户在小程序的唯一标识符（ID）。
//     * @return 一串包含库中保存的用户昵称和头像的json数组，其格式为["nickName":"Pia~","avatarUrl":"XXXXXX"].
//     */
//    @Override
//    public Response<Object> getUserInfo(String userId) {
//        return new Response<>(userMapper.getUserInfo(userId));
//    }
}
