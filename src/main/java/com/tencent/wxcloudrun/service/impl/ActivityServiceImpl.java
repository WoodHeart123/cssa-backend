package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.info;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Response getActivityList(Long current) {
        List<Activity> activityList = activityMapper.getActivityList(current);
        for(int i = 0;i < activityList.size();i++){
            activityList.get(i).setAdditionalInfo(JSON.parseArray(activityList.get(i).getAdditionalInfoJSON(),info.class));
        }
        return Response.builder().status(100).message("成功").data(activityList).build();
    }
}
