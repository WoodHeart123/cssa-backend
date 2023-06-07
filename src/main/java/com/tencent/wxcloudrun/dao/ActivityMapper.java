package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ActivityMapper {

    ArrayList<SignupInfo> checkSignup(String userID, Long date);

    /**
     * @return list of activity that is available to user
     */
    List<Activity> getActivityList();

    /**
     * @param actID activity actID
     * @return an activity with matched actID, NULL when not found
     */
    Activity findActivity(Integer actID);

    /**
     * record all user's response for one activity
     *
     * @param info information user provided
     */
    void recordResponse(SignupInfo info);

    /**
     * @param userID user ID
     * @return a list of activity user registered before
     */
    List<Activity> getRegisterList(String userID);


}