package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Transactional
    @Override
    public Response createActivity(Activity activity) {
        if (activity.getAdditionalInfo() == null) {
            activity.setAdditionalInfo(new ArrayList<>());
        }
        activity.setUserJoined(new ArrayList<>());
        activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
        activity.setUserJoinedListJSON(JSON.toJSONString(activity.getUserJoined()));
        activityMapper.createActivity(activity);
        return Response.builder().status(100).message("成功").build();
    }

    @Override
    public Response checkSignup(String actID, String userID, Long Date) {
        ArrayList<SignupInfo> result = activityMapper.checkSignup(actID,Date);
        for(int i = 0;i < result.size();i++){
            if(result.get(i).getActID().equals(actID)){
                result.get(i).setDiscount(1 - (result.size() - 1) * 0.1);
                return Response.builder().status(100).message("").data(result.get(i)).build();
            }
        }
        SignupInfo temp = new SignupInfo();
        temp.setIfJoined(false);
        temp.setDiscount(1.0);
        return Response.builder().status(100).data(temp).build();
    }
}
