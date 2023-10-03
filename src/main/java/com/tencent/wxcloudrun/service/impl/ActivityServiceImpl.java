package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
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
    public Response<SignupInfo> checkSignup(Integer actID, String userID, Long Date) {
        ArrayList<SignupInfo> result = activityMapper.checkSignup(userID, Date);
        for (SignupInfo signupInfo : result) {
            if (signupInfo.getActID().equals(actID)) {
                signupInfo.setIfJoined(true);
                return new Response<>(signupInfo);
            }
        }
        SignupInfo temp = new SignupInfo();
        temp.setIfJoined(false);
        return new Response<>(temp);
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
    public Response<List<Activity>> getRegisterList(String userID) {
        List<Activity> activityList = activityMapper.getRegisterList(userID);
        return new Response<>(activityList);
    }


}
