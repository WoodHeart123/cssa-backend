package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

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
    Response checkSignup(String actID, String userID,Long date);
}
