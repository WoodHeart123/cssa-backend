package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.service.impl.ActivityServiceImpl;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

@Mapper
public interface ActivityMapper {
    void createActivity(Activity activity);
    ArrayList<SignupInfo> checkSignup(String userID, Long date);
    List<Activity> getActivityList(Timestamp current);
    Integer updateActivity(Activity activity);
    Activity findActivity(Integer actID);
    void recordResponse(SignupInfo info);
    List<Activity> getRegisterList(String userID);
}
