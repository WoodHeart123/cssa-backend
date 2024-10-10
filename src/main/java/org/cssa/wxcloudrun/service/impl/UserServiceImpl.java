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
    public Response<Object> authSuccess(String userID) {
        userMapper.authSuccess(userID);
        return new Response<>();
    }

    @Override
    public Response<Object> login(String nickname, String userID) throws UnsupportedEncodingException {
        User user = userMapper.login(userID);
        if (user == null) {
            userMapper.register(nickname, userID);
            user = new User(nickname);
            return Response.builder().data(user).status(103).message("新用户").build();
        } else {
            if (user.getLikedCommentJSON() == null) {
                user.setLikedComment(new ArrayList<>());
            } else {
                user.setLikedComment(JSON.parseArray(user.getLikedCommentJSON(), Integer.class));
            }

        }
        return new Response<>(user);
    }

    @Override
    public Response<Object> updateEmail(String email, Boolean subscribe, String userID) {
        String regex = "^([-a-zA-Z0-9_.]+)@([-a-zA-Z0-9_.]+).([a-zA-Z]{2,5})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return Response.builder().status(104).message("邮箱格式不正确").build();
        }
        userMapper.updateEmail(email, subscribe, userID);
        return new Response<>();
    }

    @Override
    public Response<Object> updateAvatar(String userID, Integer avatar) {
        userMapper.updateAvatar(userID, avatar);
        return new Response<>();
    }

    @Override
    public Response<List<CourseComment>> getMyComment(String userID, Integer offset, Integer limit) {
        return new Response<>(userMapper.getMyComment(userID, offset, limit));
    }

    @Override
    @Transactional
    public Response<Object> updateComment(String userID, Integer commentID, String comment) {
        userMapper.updateComment(userID, commentID, comment);
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> deleteComment(String userID, Integer commentID) {
        userMapper.deleteComment(userID, commentID);
        return new Response<>();
    }

    @Override
    public Response<Object> setRentalTime(Integer rentalID, String userID, Timestamp time) {
        if (time.equals(new Timestamp(0))) {
            userMapper.clearRentalTime(rentalID, userID);
        } else {
            userMapper.setRentalTime(rentalID, userID, time);
        }
        return new Response<>();
    }

    @Override
    public Response<List<Rental>> getMyRental(String userID, Integer offset, Integer limit) {
        ArrayList<Rental> rentalList = userMapper.getMyRental(userID, offset, limit);
        for (Rental rental : rentalList) {
            rental.setImages((ArrayList<String>) JSON.parseArray(rental.getImagesJSON(), String.class));
            if (rental.getPublishedTime() == null) {
                rental.setPublishedTime(new Timestamp(0));
            }
            rental.setUTCPublishedTime(rental.getPublishedTime().toInstant().toString());
        }
        return new Response<>(rentalList);
    }

    @Override
    @Transactional
    public Response<Object> updateNickname(String userID, String nickname) {
        userMapper.updateNickname(nickname, userID);
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> updateWechatID(String wechatID, String userID) {
        userMapper.updateWechatID(wechatID, userID);
        return new Response<>();
    }

    @Override
    public Response<List<Product>> getMySecondhand(String userID, Integer offset, Integer limit) {
        List<Product> productList = userMapper.getMySecondhand(userID, offset, limit);
        for (Product product : productList) {
            product.setImages(JSON.parseArray(product.getImagesJSON(), String.class));
            if (product.getTime() == null) {
                product.setTime(new Timestamp(0));
            }
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return new Response<>(productList);
    }

    @Override
    @Transactional
    public Response<Object> updateMySecondHand(String userID, Product product) {
        userMapper.updateSecondHand(userID, product);
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> deleteMySecondHand(String userID, Integer productID) {
        userMapper.deleteSecondHand(userID, productID);
        return new Response<>();
    }

    @Override
    public Response<Object> deleteMyRental(String userID, Integer rentalID) {
        userMapper.deleteRental(userID, rentalID);
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> setProductTime(Integer productID, String userID, Timestamp time) {
        if (time.equals(new Timestamp(0))) {
            userMapper.clearProductTime(productID, userID);
        } else {
            userMapper.setProductTime(productID, userID, time);
        }
        return new Response<>();
    }

    @Override
    public Response<Boolean> subscribe(Subscription subscription) {
        String openID = subscription.getOpenID(), email = subscription.getEmail();
        if (openID.isBlank() || email.isBlank()) return new Response<>(Boolean.FALSE);

        userMapper.updateEmail(email, true, openID);

        String encryptedID = makeAnEncryptedIdforUser(openID);
        userMapper.updateEncryptedID(openID,encryptedID);
        subscription.setOpenID(encryptedID); // 使用加密ID发布事件

        applicationContext.publishEvent(new SubscriptionEvent(this, subscription, true));
        return new Response<>(Boolean.TRUE);
    }

    @Override
    public Response<Boolean> unsubscribe(String openID) {
        String email = userMapper.getEmail(openID);
        if (email.isBlank()) return new Response<>(Boolean.FALSE);

        userMapper.updateEmail(email, false, openID);

        SubscriptionEvent event = new SubscriptionEvent(this, new Subscription(openID, email), false);
        applicationContext.publishEvent(event);
        return new Response<>(Boolean.TRUE);
    }

    @Override
    public Response<Boolean> isSubscribed(String openID) {
        return new Response<>(userMapper.isSubscribed(openID));
    }

    /**
     * 为用户生成一个加密的ID。
     * 该方法使用用户的openID和当前时间戳生成一个加密ID，确保该ID在数据库中是唯一的。
     * 如果生成的加密ID已存在，则重新生成，直到产生一个唯一的加密ID。
     * 此方法不在UserService中。
     *
     * @param openID 用户的OpenID，用作生成加密ID的基础之一。
     * @return 返回生成的唯一加密ID。
     */
    public String makeAnEncryptedIdforUser(String openID) {
        boolean encryptedIDExists;
        String encryptedID;
        do {
            encryptedID = encryptionUtil.generateEncryptedID(openID, System.currentTimeMillis());
            encryptedIDExists = userMapper.ifEncryptedIDExists(encryptedID);
        } while (encryptedIDExists);
        return encryptedID;
    }

    @Override
    public Response<Boolean> isSubscribed(String openID) {
        return new Response<>(userMapper.isSubscribed(openID));
    }

    /**
     * 为用户生成一个加密的ID。
     * 该方法使用用户的openID和当前时间戳生成一个加密ID，确保该ID在数据库中是唯一的。
     * 如果生成的加密ID已存在，则重新生成，直到产生一个唯一的加密ID。
     * 此方法不在UserService中。
     *
     * @param openID 用户的OpenID，用作生成加密ID的基础之一。
     * @return 返回生成的唯一加密ID。
     */
    public String makeAnEncryptedIdforUser(String openID) {
        boolean encryptedIDExists;
        String encryptedID;
        do {
            encryptedID = encryptionUtil.generateEncryptedID(openID, System.currentTimeMillis());
            encryptedIDExists = userMapper.ifEncryptedIDExists(encryptedID);
        } while (encryptedIDExists);
        return encryptedID;
    }

   /**
     * 检查用户的邮件订阅状态。
     * <p>
     * 该方法根据用户的 openID 从数据库中获取用户的被拉黑状态并返回。默认值为0 - 否。
     *
     * @param openID 微信用户在小程序的唯一标识符。
     * @return 用户是否被拉黑。
     */
    @Override
    public Response<Boolean> isBlocked(String openID) {return new Response<>(userMapper.isBlocked(openID));}
}
