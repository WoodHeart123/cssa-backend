package com.tencent.wxcloudrun.service.impl;


import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.event.AuthEvent;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
}
