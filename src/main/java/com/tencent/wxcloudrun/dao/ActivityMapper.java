package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ActivityMapper {

    SignupInfo checkRegistration(String userID, Integer actID);

    Activity findActivity(Integer actID);

    /**
     * @return list of activity that is available to user
     */
    List<Activity> getActivityList();

    void registerActivity(SignupInfo signupInfo);

    /**
     * @param userID user ID
     * @return a list of activity user registered before
     */
    List<Activity> getRegisterList(String userID);


}