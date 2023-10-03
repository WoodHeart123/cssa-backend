package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminMapper {

    /**
     * create activity with provided info
     *
     * @param activity all activity info
     */
    void createActivity(Activity activity);

    /**
     * get all activity list
     */
    List<Activity> getActivityList();

    /**
     * login method for admin
     *
     * @param admin password and username
     * @return 0 for mismatch, 1 for success
     */
    Admin login(Admin admin);

    //Integer checkUsername(String username);

    void register(Admin admin);

    /**
     * delete the activity by setting timestamp to zero
     *
     * @param actID     actID
     * @param timestamp timestamp(0)
     */
    void deleteActivity(@Param("actID") String actID, @Param("timestamp") Timestamp timestamp);

    /**
     * get all user list that register the activity
     *
     * @param actID actID
     */
    ArrayList<SignupInfo> getActivitySignup(String actID);

    /**
     * delete the comment by comment ID
     *
     * @param commentID
     */
    void deleteComment(Integer commentID);

    List<Department> getDepartmentList();

    List<Course> getCourseList(Integer departmentID);

    List<CourseComment> getCommentList(Integer courseID);
}
