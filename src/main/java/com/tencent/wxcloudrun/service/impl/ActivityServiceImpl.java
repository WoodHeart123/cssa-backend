package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.PaymentOption;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Response<SignupInfo> checkSignup(String userID, Integer actID) {
        SignupInfo result = activityMapper.checkRegistration(userID, actID);
        if (result == null) {
            result = new SignupInfo();
            result.setIfJoined(false);
        }else{
            result.setIfJoined(true);
        }
        return new Response<>(result);
    }



    @Override
    public Response<List<Activity>> getActivityList() {
        List<Activity> activityList = activityMapper.getActivityList();
        for (Activity activity : activityList) {
            activity.setPayment(JSON.parseObject(activity.getPaymentJSON(), PaymentOption.class));
            activity.setImages((ArrayList<String>) JSON.parseArray(activity.getImagesJSON(), String.class));
        }
        return new Response<>(activityList);
    }

    @Override
    public Response<Object> registerActivity(SignupInfo signupInfo) {
        activityMapper.registerActivity(signupInfo);
        return new Response<>();
    }

    @Override
    public Response<Object> cancelRegister(String userID, Integer actID) {
        activityMapper.cancelRegister(userID, actID);
        return new Response<>();
    }

    @Override
    public Response<List<Activity>> getRegisterList(String userID) {
        List<Activity> activityList = activityMapper.getRegisterList(userID);
        return new Response<>(activityList);
    }


}
