package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.EmailDetail;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.service.ActivityService;
import com.tencent.wxcloudrun.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping({"/activity"})
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    EmailService emailService;

    @RequestMapping(value={"/login"}, method={RequestMethod.GET})
    public Response login(@RequestParam String nickname, HttpServletRequest request) throws UnsupportedEncodingException {
        Optional<String> openid = Optional.ofNullable(request.getHeader(""));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return activityService.login(nickname, openid.get());
    }

    @RequestMapping(value={"/updateEmail"}, method={RequestMethod.GET})
    public Response updateEmail(String email, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return activityService.updateEmail(email,openid.get());
    }


    @RequestMapping(value={"/checksignup"},method={RequestMethod.GET})
    public Response checkSignup(@RequestParam(name="actID") Integer actID, @RequestParam(name="date") Long date, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return activityService.checkSignup(actID,openid.get(),date);
    }

    @RequestMapping(value = {"/detail"}, method = {RequestMethod.GET})
    public Response Detail(HttpServletRequest request) {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        return Response.builder().data(openid).message("success").build();
    }


    @RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
    public Response register(@RequestBody SignupInfo info, HttpServletRequest request) {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        info.setUserID(openid.get());
        return activityService.registerActivity(info);
    }

    @RequestMapping(value = {"/activityList"}, method = {RequestMethod.GET})
    public Response findByID(@RequestParam Long current,HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return activityService.getActivityList(new Timestamp(current),openid.get());
    }

    @RequestMapping(value = {"/registerList"}, method = {RequestMethod.GET})
    public Response getRegisterList(HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return activityService.getRegisterList(openid.get());
    }
}
