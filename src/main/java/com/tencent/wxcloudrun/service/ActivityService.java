package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public interface ActivityService {
    /**
     * create a new activity
     * @param activity all attributes needed to describe activity
     */
    Response createActivity(Activity activity);

    /**
     * check whether user has signed up for the activity
     * @return
     */
    Response checkSignup(Integer actID, String userID,Long date);

    Response getActivityList(Timestamp current, String userID);

    /**
     * sign up for an activity
     * @return
     */
    Response registerActivity(SignupInfo info);

    Response getRegisterList(String userID);
}
