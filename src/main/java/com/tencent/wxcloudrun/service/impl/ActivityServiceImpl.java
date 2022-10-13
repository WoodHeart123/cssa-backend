package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.event.SignupEvent;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Response checkSignup(Integer actID, String userID, Long Date) {
        ArrayList<SignupInfo> result = activityMapper.checkSignup(userID, Date);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getActID().equals(actID)) {
                result.get(i).setDiscount(1 - (result.size() - 1) * 0.1);
                result.get(i).setIfJoined(true);
                return Response.builder().status(100).data(result.get(i)).build();
            }
        }
        SignupInfo temp = new SignupInfo();
        temp.setIfJoined(false);
        temp.setDiscount(1 - result.size() * 0.1);
        return Response.builder().status(100).data(temp).build();
    }

    @Override
    public Response getActivityList(Timestamp current, String userID) {
        List<Activity> activityList = activityMapper.getActivityList(current);
        List<Activity> joinList = activityMapper.getRegisterList(userID);
        int i = 0,j = 0;
        while(i < activityList.size() && j < joinList.size()){
            if(activityList.get(i).getActID().equals(joinList.get(j).getActID())){
                activityList.remove(i);
                j++;
            }else if(activityList.get(i).getActID().compareTo(joinList.get(j).getActID()) > 0){
                j++;
            }else{
                i++;
            }
        }
        for(i = 0;i < activityList.size();i++){
            activityList.get(i).setAdditionalInfo(JSON.parseArray(activityList.get(i).getAdditionalInfoJSON(), Info.class));
        }
        return Response.builder().status(100).message("成功").data(activityList).build();
    }

    @Override
    public Response registerActivity(SignupInfo info) {
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
            applicationContext.publishEvent(new SignupEvent(this,info));
            return Response.builder().status(100).message("成功").build();

        } catch (Exception exception) {
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response getRegisterList(String userID){
        List<Activity> activityList = activityMapper.getRegisterList(userID);
        return Response.builder().status(100).data(activityList).message("成功").build();
    }


}
