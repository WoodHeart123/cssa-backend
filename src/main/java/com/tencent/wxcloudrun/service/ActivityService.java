package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService {

    /**
     * check whether user has signed up for the activity
     *
     * @return
     */
    Response<SignupInfo> checkSignup(Integer actID, String userID, Long date);

    Response<List<Activity>> getActivityList();

    /**
     * @param userID open id
     * @return a list of activity user registered
     */
    Response<List<Activity>> getRegisterList(String userID);


}
