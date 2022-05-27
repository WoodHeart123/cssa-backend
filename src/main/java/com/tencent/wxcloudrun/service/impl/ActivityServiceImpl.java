package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
            activity.setUserJoined(new ArrayList<>());
            activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
            activity.setUserJoinedListJSON(JSON.toJSONString(activity.getUserJoined()));
            activityMapper.createActivity(activity);
            return Response.builder().status(100).message("成功").build();
        }catch(Exception exception){
            return Response.builder().status(101).message("意料之外的错误").build();
        }
    }
}
