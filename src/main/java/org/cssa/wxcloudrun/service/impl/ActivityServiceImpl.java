package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import org.cssa.wxcloudrun.dao.ActivityMapper;
import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.PaymentOption;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.ReturnCode;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.cssa.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Response<SignupInfo> checkSignup(String userID, Integer actID) {
        SignupInfo result = activityMapper.checkRegistration(userID, actID);
        if (result == null) {
            result = new SignupInfo();
            result.setIfJoined(false);
        } else {
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
        Activity act = activityMapper.findActivity(signupInfo.getActID());
        if (act == null) {
            return new Response<>(ReturnCode.ACTIVITY_NOT_EXIST);
        }
        if (act.getDeadline().before(new Timestamp(System.currentTimeMillis()))) {
            return new Response<>(ReturnCode.DEADLINE_PASSED);
        }
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
