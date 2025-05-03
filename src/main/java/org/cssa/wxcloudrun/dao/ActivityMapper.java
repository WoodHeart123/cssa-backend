package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.SignupInfo;

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


}