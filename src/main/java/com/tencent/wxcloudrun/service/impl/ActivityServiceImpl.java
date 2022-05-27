package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Response createActivity(Activity activity) {
        try {
            if (activity.getAdditionalInfo() == null) {
                //activity.setAdditionalInfo(new ArrayList<>());
            }
            activity.setUserJoined(new ArrayList<>());
            activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
            activity.setUserJoinedListJSON(JSON.toJSONString(activity.getUserJoined()));
            activityMapper.createActivity(activity);
            return Response.builder().status(100).message("成功").build();
        }catch(Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response findByID(int id) {
        Activity activity = activityMapper.findByID(id);
        if (activity != null) {
            return Response.builder().status(100).message("成功").data(activity).build();
        } else {
            return Response.builder().status(505).message("没有这个活动").build();
        }
    }
}
