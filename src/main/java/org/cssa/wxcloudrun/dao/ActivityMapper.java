package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * 寻找已删除的活动
     * @param actID 活动ID
     * @return 活动详情
     */
    Activity findDeletedActivity(Integer actID);

    /**
     * 获取给定活动ID的活动详情
     * @param actID 活动ID
     * @return 活动详情
     */
    Activity getActivityDetails(Integer actID);

    /**
     * 获取所有还未结束的活动
     * @return 未结束活动的列表
     */
    List<Activity> getOngoingActivities();

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