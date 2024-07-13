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

    /**
     * 订阅用户的邮件地址。
     *
     * 该方法更新数据库中用户的邮箱地址，并将用户的订阅状态设置为已订阅。
     * 然后发布一个订阅事件。
     *
     * @param subscription 包含用户订阅信息的对象，其中包括用户的邮箱地址和 openID。
     * @return 返回一个包含订阅操作结果的响应对象。如果操作成功，返回 `Response<Boolean>` 包含 `true`。
     */
    @Override
    public Response<Boolean> subscribe(Subscription subscription) {
        if (subscription.getEmail().isBlank() || subscription.getOpenID().isBlank()) return new Response<>(Boolean.FALSE);

        userMapper.updateEmail(subscription.getEmail(), true, subscription.getOpenID());
        applicationContext.publishEvent(new SubscriptionEvent(this, subscription, true));
        return new Response<>(Boolean.TRUE);
    }

    /**
     * 取消用户的邮件订阅。
     *
     * 该方法根据用户的 openID 从数据库中获取用户的邮箱地址，并将用户的订阅状态设置为未订阅。
     * 然后发布一个取消订阅事件。
     *
     * @param openID 要取消订阅的用户的唯一标识符。
     * @return 返回一个包含取消订阅操作结果的响应对象。如果操作成功且用户存在，返回 `Response<Boolean>` 包含 `true`；否则返回 `false`。
     */
    @Override
    public Response<Boolean> unsubscribe(String openID) {
        String email = userMapper.getEmail(openID);
        if (email.isBlank()) return new Response<>(Boolean.FALSE);

        userMapper.updateEmail(email, false, openID);
        SubscriptionEvent event = new SubscriptionEvent(this, new Subscription(openID, email), false);
        applicationContext.publishEvent(event);
        return new Response<>(Boolean.TRUE);
    }
}
