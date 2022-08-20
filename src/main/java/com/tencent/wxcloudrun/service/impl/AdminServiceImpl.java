package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.AdminMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Admin;
import com.tencent.wxcloudrun.model.Jwtutil;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    Jwtutil jwtutil;

    @Override
    public Response getActivityList() {
        return Response.builder().data(adminMapper.getActivityList()).status(100).build();
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
        return null;
    }

    @Override
    public Response login(Admin admin) {
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        Integer result = this.adminMapper.login(admin);
        if(result.equals(1)){
            return Response.builder().data(jwtutil.generateToken(admin.getUsername())).status(100).build();
        }else{
            return Response.builder().status(301).message("用户名密码错误").build();
        }
    }
}
