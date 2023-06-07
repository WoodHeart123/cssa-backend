package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.service.ActivityService;
import com.tencent.wxcloudrun.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/activity"})
@Api(tags = "活动中心")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    EmailService emailService;

    @Operation(summary = "查看用户报名信息", description = "查看用户报名信息")
    @RequestMapping(value = {"/checksignup"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<SignupInfo> checkSignup(@ApiParam(value = "活动ID", required = true) @RequestParam(name = "actID") Integer actID,
                                            @ApiParam(value = "时间", required = true) @RequestParam(name = "date") Long date, @ApiParam(value = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.checkSignup(actID, openid, date);
    }


    @Operation(summary = "报名活动", description = "根据提供的信息报名对应的活动")
    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public Response<Object> register(@ApiParam(value = "报名信息", required = true) @RequestBody SignupInfo info, @ApiParam(value = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        info.setUserID(openid);
        return activityService.registerActivity(info);
    }

    @Operation(summary = "获取活动列表", description = "获取正在进行的活动列表。活动开始日期大于现在时间。")
    @RequestMapping(value = {"/activityList"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Activity>> getActivityList(@Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.getActivityList(openid);
    }


    @Operation(summary = "获取报名列表", description = "获得用户所有报名活动列表")
    @RequestMapping(value = {"/registerList"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Activity>> getRegisterList(@Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.getRegisterList(openid);
    }
}
