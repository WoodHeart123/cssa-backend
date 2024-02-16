package org.cssa.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import org.cssa.wxcloudrun.dao.AdminMapper;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    Jwtutil jwtutil;

    public Response<Object> register(Admin admin) {
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        adminMapper.register(admin);
        return new Response<>(ReturnCode.SUCCESS);
    }

    @Override
    public Response<List<Activity>> getActivityList() {
        List<Activity> activityList = adminMapper.getActivityList();
        return new Response<>(activityList);
    }

    @Override
    public Response<Object> createActivity(Activity activity) {
        try {
            if (activity.getAdditionalInfoJSON() == null) {
                activity.setAdditionalInfoJSON("{}");
            }
            activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfoJSON()));
            adminMapper.createActivity(activity);
            return Response.builder().status(100).message("成功").build();
        } catch (Exception exception) {
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response<List<SignupInfo>> getActivitySignup(String actID) {
        ArrayList<SignupInfo> list = adminMapper.getActivitySignup(actID);
        return new Response<>(list);
    }

    @Override
    public Response<Admin> login(Admin admin) {
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        Admin result = this.adminMapper.login(admin);
        if (result != null) {
            admin.setToken(jwtutil.generateToken(admin.getUsername()));
            return new Response<>(admin);
        } else {
            return new Response<>(ReturnCode.INVALID_ADMIN_INFO);
        }
    }

    @Override
    @Transactional
    public Response<Object> deleteActivity(String actID) {
        this.adminMapper.deleteActivity(actID, new Timestamp(0));
        return Response.builder().status(100).build();
    }


    @Override
    public Response<List<Department>> getDepartmentList() {
        List<Department> departmentList = adminMapper.getDepartmentList();
        return new Response<>(departmentList);
    }

    @Override
    public Response<List<Course>> getCourseList(Integer departmentID) {
        return new Response<>(adminMapper.getCourseList(departmentID));
    }

    @Override
    public Response<List<CourseComment>> getCommentList(Integer courseID) {
        return new Response<>(adminMapper.getCommentList(courseID));
    }

    @Override
    public Response<Object> deleteComment(Integer commentID) {
        adminMapper.deleteComment(commentID);
        return Response.builder().status(100).message("success").build();
    }
}
