package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.event.AuthEvent;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public Response getLikedCommentList(String userID) {
        User user = userMapper.getLikedCommentList(userID);
        user.setLikedComment(JSON.parseArray(user.getLikedCommentJSON(), Integer.class));
        return new Response(user.getLikedComment());
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
        }
        return new Response(productList);
    }

    @Override
    @Transactional
    public Response updateMySecondHand(String userID, Product product) {
        userMapper.updateMySecondHand(userID, product);
        return new Response();
    }

    @Override
    @Transactional
    public Response deleteMySecondHand(String userID, Integer productID) {
        userMapper.deleteMySecondHand(userID,productID);
        return new Response();
    }

    @Override
    public Response getMyProductSave(String userID, Integer offset, Integer limit){
        User user = userMapper.collect(userID);
        user.setSavedProduct(JSON.parseArray(user.getSavedProductJSON(), Integer.class));
        List<Product> productList = userMapper.getMyProductSave(offset, limit, user.getSavedProduct());
        for(Product product:productList){
            product.setImages(JSON.parseArray(product.getImagesJSON(),String.class));
        }
        return new Response(productList);
    }

    @Override
    public Response delMyProductSave(String userID, Integer productID){
        return null;
    }
    @Override
    public Response getMyRentalSave(String userID, Integer offset, Integer limit){
        User user = userMapper.collect(userID);
        user.setSavedRental(JSON.parseArray(user.getSavedRentalJSON(), Integer.class));
        List<Rental> rentalList = userMapper.getMyRentalSave(offset, limit, user.getSavedProduct());
        for(Rental rental:rentalList){
            rental.setImages(JSON.parseArray(rental.getImagesJSON(),String.class));
        }
        return new Response(rentalList);
    }
}
