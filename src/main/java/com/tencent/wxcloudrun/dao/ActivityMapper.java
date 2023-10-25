package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ActivityMapper {

    SignupInfo checkRegistration(String userID, Integer actID);

    Activity findActivity(Integer actID);

    void cancelRegister(String userID, Integer actID);

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

    /**
     * 获取活动详情
     * @param actID 活动ID
     * @return 活动详情
     */
    Activity getActivityDetails(Integer actID);

    /**
     * Update all details of an activity given its id.
     * @param actID 活动ID
     * @param activity 更新后的活动信息
     */
    void updateFullActivity(@Param("actID") Integer actID, @Param("activity") Activity activity);

    /**
     * Soft-delete an activity given its id
     * @param actID 活动ID
     */
    void deleteActivity(Integer actID);
}