package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.event.SignupEvent;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Info;
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

    Queue<Objects> q = new LinkedList<>();

    @Override
    public Response<SignupInfo> checkSignup(Integer actID, String userID, Long Date) {
        ArrayList<SignupInfo> result = activityMapper.checkSignup(userID, Date);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getActID().equals(actID)) {
                result.get(i).setDiscount(1 - (result.size() - 1) * 0.1);
                result.get(i).setIfJoined(true);
                return new Response<>(result.get(i));
            }
        }
        SignupInfo temp = new SignupInfo();
        temp.setIfJoined(false);
        temp.setDiscount(1 - result.size() * 0.1);
        return new Response<>(temp);
    }

    @Override
    public Response<List<Activity>> getActivityList(String userID) {
        List<Activity> activityList = activityMapper.getActivityList();
        List<Activity> joinList = activityMapper.getRegisterList(userID);
        int i = 0, j = 0;
        while (i < activityList.size() && j < joinList.size()) {
            if (activityList.get(i).getActID().equals(joinList.get(j).getActID())) {
                activityList.remove(i);
                j++;
            } else if (activityList.get(i).getActID().compareTo(joinList.get(j).getActID()) > 0) {
                j++;
            } else {
                i++;
            }
        }
        for (i = 0; i < activityList.size(); i++) {
            activityList.get(i).setAdditionalInfo(JSON.parseArray(activityList.get(i).getAdditionalInfoJSON(), Info.class));
        }
        return new Response<>(activityList);
    }

    @Override
    public Response<Object> registerActivity(SignupInfo info) {
        try {
            Activity activity = activityMapper.findActivity(info.getActID());

            // edge cases
            if (activity == null) {
                return Response.builder().status(101).message("活动不存在").build();
            }
            if (activity.getCapacity() <= activity.getUserJoinedNum()) {
                return Response.builder().status(103).message("活动人数已满").build();
            }
            info.setResponseJSON(JSON.toJSONString(info.getResponse()));
            activityMapper.recordResponse(info);
            applicationContext.publishEvent(new SignupEvent(this, info));
            return Response.builder().status(100).message("成功").build();

        } catch (Exception exception) {
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response<List<Activity>> getRegisterList(String userID) {
        List<Activity> activityList = activityMapper.getRegisterList(userID);
        return new Response<>(activityList);
    }


}
