package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.event.AuthEvent;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.RentalService;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPooled;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JedisPooled jedisPooled;


    @Override
    public void getAuthCode(String email, Integer authCode) {
        applicationContext.publishEvent(new AuthEvent(this,email,authCode));
    }

    @Override
    public Response authSuccess(String userID) {
        userMapper.authSuccess(userID);
        return new Response();
    }
    @Override
    public Response login(String nickname, String userID) throws UnsupportedEncodingException {
        User user = userMapper.login(userID);
        if(user == null){
            userMapper.register(nickname,userID);
            user = new User(nickname);
            return Response.builder().data(user).status(103).message("新用户").build();
        }else{
            if(user.getLikedCommentJSON() == null){
                user.setLikedComment(new ArrayList<>());
            }else {
                user.setLikedComment(JSON.parseArray(user.getLikedCommentJSON(), Integer.class));
            }

        }
        return new Response(user);
    }

    @Override
    public Response updateEmail(String email,Boolean subscribe, String userID){
        String regex = "^([-a-zA-Z0-9_.]+)@([-a-zA-Z0-9_.]+).([a-zA-Z]{2,5})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return Response.builder().status(104).message("邮箱格式不正确").build();
        }
        userMapper.updateEmail(email, subscribe, userID);
        return new Response();
    }

    @Override
    public Response updateAvatar(String userID,Integer avatar){
        userMapper.updateAvatar(userID,avatar);
        return new Response();
    }

    @Override
    public Response getMyComment(String userID, Integer offset, Integer limit) {
        return new Response(userMapper.getMyComment(userID,offset,limit));
    }

    @Override
    @Transactional
    public Response updateComment(String userID, Integer commentID, String comment) {
        userMapper.updateComment(userID,commentID,comment);
        return new Response();
    }

    @Override
    @Transactional
    public Response deleteComment(String userID, Integer commentID) {
        userMapper.deleteComment(userID,commentID);
        return new Response();
    }

    @Override
    public Response setRentalTime(Integer rentalID, String userID, Timestamp time) {
        if(time.equals(new Timestamp(0))){
            userMapper.clearProductTime(rentalID, userID);
        }else {
            userMapper.setProductTime(rentalID, userID, time);
        }
        return new Response();
    }

    @Override
    public Response getMyRental(String userID, Integer offset, Integer limit) {
        ArrayList<Rental> rentalList = userMapper.getMyRental(userID, offset,limit);
        for(Rental rental: rentalList){
            rental.setImages((ArrayList<String>) JSON.parseArray(rental.getImagesJSON(),String.class));
            if(rental.getPublishedTime() == null){
                rental.setPublishedTime(new Timestamp(0));
            }
            rental.setUTCPublishedTime(rental.getPublishedTime().toInstant().toString());
        }
        return new Response(rentalList);
    }

    @Override
    @Transactional
    public Response updateNickname(String userID, String nickname) {
        userMapper.updateNickname(nickname,userID);
        return new Response();
    }

    @Override
    @Transactional
    public Response updateWechatID(String wechatID, String userID){
        userMapper.updateWechatID(wechatID, userID);
        return new Response();
    }

    @Override
    public Response getMySecondhand(String userID, Integer offset, Integer limit) {
        List<Product> productList = userMapper.getMySecondhand(userID,offset,limit);
        for(Product product:productList){
            product.setImages(JSON.parseArray(product.getImagesJSON(),String.class));
            if(product.getTime() == null) {
                product.setTime(new Timestamp(0));
            }
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return new Response(productList);
    }

    @Override
    @Transactional
    public Response updateMySecondHand(String userID, Product product) {
        userMapper.updateSecondHand(userID, product);
        return new Response();
    }

    @Override
    @Transactional
    public Response deleteMySecondHand(String userID, Integer productID) {
        userMapper.deleteSecondHand(userID,productID);
        return new Response();
    }

    @Override
    public Response deleteMyRental(String userID, Integer rentalID) {
        userMapper.deleteRental(userID,rentalID);
        return new Response();
    }

    @Override
    @Transactional
    public Response setProductTime(Integer productID, String userID, Timestamp time) {
        if(time.equals(new Timestamp(0))){
            userMapper.clearProductTime(productID, userID);
        }else {
            userMapper.setProductTime(productID, userID, time);
        }
        return new Response();
    }

    @Override
    @Transactional
    public Response collect(Collect collect, Boolean save) {
        if(save){
            userMapper.addCollect(collect);
        }else{
            userMapper.deleteCollect(collect);
        }
        return new Response();
    }

    @Override
    public Response getCollectID(CollectType collectType, String userID) {
        return new Response(userMapper.getCollectID(collectType, userID));
    }

    @Override
    public Response getCollectList(CollectType collectType, String userID, Integer offset, Integer limit) {
        if (collectType == CollectType.SECONDHAND) {
            List<Product> result = userMapper.getProductCollectList(collectType, userID, offset, limit);
            for(Product product: result) {
                product.setImages(JSON.parseArray(product.getImagesJSON(), String.class));
            }
            return new Response(result);
        } else if (collectType == CollectType.RENTAL) {
            List<Rental> result = userMapper.getRentalCollectList(collectType, userID, offset, limit);
            for(Rental rental :result){
                rental.setImages((ArrayList<String>) JSON.parseArray(rental.getImagesJSON(),String.class));
            }
            return new Response(result);
        } else {
            return new Response(ReturnCode.INVALID_ENUM_TYPE);
        }

    }
}
