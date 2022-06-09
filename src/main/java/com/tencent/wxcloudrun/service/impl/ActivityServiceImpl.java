package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.model.Info;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Response createActivity(Activity activity) {
        try {
            if (activity.getAdditionalInfo() == null) {
                activity.setAdditionalInfo(new ArrayList<>());
            }
            activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
            activityMapper.createActivity(activity);
            return Response.builder().status(100).message("成功").build();
        }catch(Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response checkSignup(String actID, String userID, Long Date) {
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
        temp.setDiscount(1.0);
        return Response.builder().status(100).data(temp).build();
    }

    @Override
    public Response getActivityList(Timestamp current) {
        List<Activity> activityList = activityMapper.getActivityList(current);
        for(int i = 0;i < activityList.size();i++){
            activityList.get(i).setAdditionalInfo(JSON.parseArray(activityList.get(i).getAdditionalInfoJSON(), Info.class));
        }
        return Response.builder().status(100).message("成功").data(activityList).build();
    }

    @Override
    public Response regsiterActivity(SignupInfo info) {
        try {
            Activity activity = activityMapper.findActivity(info.getActID());

            // edge cases
            if (activity == null) {
                return Response.builder().status(101).message("活动不存在").build();
            }
            if (activity.getCapacity() <= activity.getUserJoinedNum()) {
                return Response.builder().status(101).message("活动人数已满").build();
            }

            activity.setUserJoinedNum(activity.getUserJoinedNum() + 1);
            activityMapper.updateActivity(activity);
            activityMapper.recordResponse(info);
            return Response.builder().status(100).message("成功").build();

        } catch (Exception exception) {
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }
}
