package org.cssa.wxcloudrun.service;


import org.cssa.wxcloudrun.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    Response<List<Activity>> getActivityList();

    /**
     * create a new activity
     *
     * @param activity all attributes needed to describe activity
     */
    Response<Object> createActivity(Activity activity);

    Response<List<SignupInfo>> getActivitySignup(String actID);

    Response<Admin> login(Admin admin);

    Response<Object> register(Admin admin);

    Response<Object> deleteActivity(String actID);

    Response<Object> deleteComment(Integer commentID);

    Response<List<Department>> getDepartmentList();

    Response<List<Course>> getCourseList(Integer departmentID);

    Response<List<CourseComment>> getCommentList(Integer courseID);
}
