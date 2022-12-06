package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Admin;
import com.tencent.wxcloudrun.model.MainPagePhoto;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminMapper {

    /**
     * create activity with provided info
     * @param activity all activity info
     */
    void createActivity(Activity activity);

    /**
     * get all activity list
     */
    List<Activity> getActivityList();

    /**
     * login method for admin
     * @param admin password and username
     * @return 0 for mismatch, 1 for success
     */
    Integer login(Admin admin);

    Integer checkUsername(String username);

    void register(Admin admin);

    /**
     * delete the activity by setting timestamp to zero
     * @param actID actID
     * @param timestamp timestamp(0)
     */
    void deleteActivity(@Param("actID") String actID,@Param("timestamp") Timestamp timestamp);

    /**
     * get all user list that register the activity
     * @param actID actID
     */
    ArrayList<SignupInfo> getActivitySignup(String actID);

//    void postMainPagePhoto(MainPagePhoto mainPagePhoto);

    void deleteMainPagePhoto(@Param("photoID") String actID,@Param("timestamp") Timestamp timestamp);


}
