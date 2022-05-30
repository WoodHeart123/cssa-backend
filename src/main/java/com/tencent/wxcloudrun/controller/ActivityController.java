package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Jwtutil;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping({"/activity"})
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    Jwtutil jwtutil;

    @RequestMapping(value={"/createActivity"}, method={RequestMethod.POST})
    public Response createActivity(@RequestBody Activity activity){
        return activityService.createActivity(activity);
    }

    @RequestMapping(value={"/checksignup"},method={RequestMethod.GET})
    public Response checkSignup(@RequestParam(name="actID") String actID, @RequestParam(name="date") Long date, HttpServletRequest request){
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

    @RequestMapping(value = {"/activityList"}, method = {RequestMethod.GET})
    public Response findByID(@RequestParam Long current){
        return activityService.getActivityList(current);
    }
}
