package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.event.AuthEvent;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
        return Response.builder().status(100).build();
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
        return Response.builder().status(100).data(user).build();
    }

    @Override
    public Response updateEmail(String email,String userID){
        String regex = "^([-a-zA-Z0-9_.]+)@([-a-zA-Z0-9_.]+).([a-zA-Z]{2,5})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return Response.builder().status(104).message("邮箱格式不正确").build();
        }
        userMapper.updateEmail(email,userID);
        return Response.builder().status(100).build();
    }

    @Override
    public Response getLikedCommentList(String userID) {
        User user = userMapper.getLikedCommentList(userID);
        user.setLikedComment(JSON.parseArray(user.getLikedCommentJSON(), Integer.class));
        return Response.builder().data(user.getLikedComment()).status(100).build();
    }

    @Override
    public Response updateAvatar(String userID,Integer avatar){
        userMapper.updateAvatar(userID,avatar);
        return Response.builder().status(100).build();
    }

    @Override
    public Response getMyComment(String userID, Integer offset, Integer limit) {
        return Response.builder().status(100).data(userMapper.getMyComment(userID,offset,limit)).build();
    }

    @Override
    public Response updateComment(String userID, Integer commentID, String comment) {
        userMapper.updateComment(userID,commentID,comment);
        return Response.builder().status(100).build();
    }

    @Override
    public Response deleteComment(String userID, Integer commentID) {
        userMapper.deleteComment(userID,commentID);
        return Response.builder().status(100).build();
    }

    @Override
    public Response updateNickname(String userID, String nickname) {
        userMapper.updateNickname(nickname,userID);
        return Response.builder().status(100).build();
    }


}
