package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService {

    /**
     * check whether user has signed up for the activity
     *
     * @return
     */
    Response<SignupInfo> checkSignup(String userID, Integer actID);

    Response<List<Activity>> getActivityList();

    Response<Activity> getActivity(Integer actID);

    Response<Object> registerActivity(SignupInfo signupInfo);

    Response<Object> cancelRegister(String userID, Integer actID);

    /**
     * @param userID open id
     * @return a list of activity user registered
     */
    Response<List<Activity>> getRegisterList(String userID);


}
