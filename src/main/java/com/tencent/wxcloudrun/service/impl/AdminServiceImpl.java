package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.AdminMapper;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.AdminService;
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

    public Response register(Admin admin){
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        adminMapper.register(admin);
        return new Response(ReturnCode.SUCCESS);
    }
    @Override
    public Response getActivityList() {
        List<Activity> activityList = adminMapper.getActivityList();
        for(Activity activity: activityList){
            activity.setAdditionalInfo(JSON.parseArray(activity.getAdditionalInfoJSON(),Info.class));
        }
        return Response.builder().data(activityList).status(100).build();
    }

    @Override
    public Response createActivity(Activity activity) {
        try {
            if (activity.getAdditionalInfo() == null) {
                activity.setAdditionalInfo(new ArrayList<>());
            }
            activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
            adminMapper.createActivity(activity);
            return Response.builder().status(100).message("成功").build();
        }catch(Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response getActivitySignup(String actID) {
        ArrayList<SignupInfo> list = adminMapper.getActivitySignup(actID);
        for (SignupInfo signupInfo : list) {
            if(signupInfo.getResponseJSON() != null){
                signupInfo.setResponse(JSON.parseArray(signupInfo.getResponseJSON(),String.class));
            }
        }
        return Response.builder().status(100).data(list).build();
    }

    @Override
    public Response login(Admin admin) {
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        Admin result = this.adminMapper.login(admin);
        if(result != null){
            admin.setToken(jwtutil.generateToken(admin.getUsername()));
            return Response.builder().data(admin).status(100).build();
        }else{
            return Response.builder().status(301).message("用户名密码错误").build();
        }
    }

    @Override
    @Transactional
    public Response deleteActivity(String actID) {
        this.adminMapper.deleteActivity(actID, new Timestamp(0));
        return Response.builder().status(100).build();
    }

    @Override
    public Response postMainPagePhoto(MainPagePhoto mainPagePhoto) {
        adminMapper.postMainPagePhoto(mainPagePhoto);
        return Response.builder().status(100).message("成功").build();
    }

    @Override
    @Transactional
    public Response deleteMainPagePhoto(String photoID) {
        this.adminMapper.deleteMainPagePhoto(photoID, new Timestamp(0));
        return Response.builder().status(100).build();
    }

    @Override
    public Response getMainPagePhotoList(Integer offset, Integer limit) {
        List<MainPagePhoto> mainPagePhotoList = adminMapper.getMainPagePhotoList(offset, limit);
        return Response.builder().data(mainPagePhotoList).status(100).build();
    }

    @Override
    public Response getDepartmentList() {
        List<Department> departmentList = adminMapper.getDepartmentList();
        return Response.builder().data(departmentList).status(100).message("成功").build();
    }

    @Override
    public Response getCourseList(Integer departmentID) {
        return Response.builder().data(adminMapper.getCourseList(departmentID)).status(100).message("成功").build();
    }

    @Override
    public Response getCommentList(Integer courseID) {
        return Response.builder().data(adminMapper.getCommentList(courseID)).status(100).message("成功").build();
    }

    @Override
    public Response deleteComment(Integer commentID) {
        adminMapper.deleteComment(commentID);
        return Response.builder().status(100).message("success").build();
    }
}
