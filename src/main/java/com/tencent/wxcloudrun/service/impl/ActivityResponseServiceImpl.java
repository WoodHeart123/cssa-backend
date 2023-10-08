package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.dao.ActivityResponseMapper;
import com.tencent.wxcloudrun.event.SignupEvent;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.ActivityResponseService;
import com.tencent.wxcloudrun.service.ActivityService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityResponseServiceImpl implements ActivityResponseService {

    @Autowired
    private ActivityResponseMapper activityResponseMapper;

    @Override
    public void createResponse(Integer ID, ActivityResponse activityResponse) {

    }

    @Override
    public List<ActivityResponse> selectResponseList(Integer activityId) {
        return null;
    }

    @Override
    public ActivityResponse selectResponseContent(Integer activityID) {
        return null;
    }

    @Override
    public void updateResponse(ActivityResponse activityResponse) {

    }

    @Override
    public void deleteResponse(Integer activityID) {

    }
}
