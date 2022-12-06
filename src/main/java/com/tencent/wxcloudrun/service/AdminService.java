package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Admin;
import com.tencent.wxcloudrun.model.MainPagePhoto;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Response getActivityList();

    /**
     * create a new activity
     * @param activity all attributes needed to describe activity
     */
    Response createActivity(Activity activity);

    Response getActivitySignup(String actID);

    Response login(Admin admin);

    Response deleteActivity(String actID);

    Response postMainPagePhoto(MainPagePhoto mainPagePhoto);

    Response deleteMainPagePhoto(String photoID);
}
