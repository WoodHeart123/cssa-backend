package com.tencent.wxcloudrun.service.impl;


import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.event.AuthEvent;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
            return Response.builder().status(103).message("新用户").build();
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

}
