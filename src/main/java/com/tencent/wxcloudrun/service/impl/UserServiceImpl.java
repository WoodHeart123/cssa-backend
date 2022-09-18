package com.tencent.wxcloudrun.service.impl;


import com.tencent.wxcloudrun.event.AuthEvent;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ApplicationContext applicationContext;


    @Override
    public void getAuthCode(String email, Integer authCode) {
        applicationContext.publishEvent(new AuthEvent(this,email,authCode));
    }
}
