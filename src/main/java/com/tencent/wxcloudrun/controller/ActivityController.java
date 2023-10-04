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
    @RequestMapping(value = {"/events/signup/{act_id}"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<SignupInfo> checkSignup(@ApiParam(value = "活动ID", required = true) @PathVariable(name = "act_id") Integer actID
                                            , @ApiParam(value = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.checkSignup(openid, actID);
    }


    @Operation(summary = "获取活动列表", description = "获取正在进行的活动列表。活动开始日期大于现在时间。")
    @RequestMapping(value = {"/events"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Activity>> getActivityList() {
        return activityService.getActivityList();
    }

    @Operation(summary = "报名活动", description = "记录用户的报名信息")
    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST}, produces = "application/json")
    public Response<Object> registerActivity(@RequestBody SignupInfo signupInfo,
                                                    @Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        signupInfo.setUserID(openid);
        return activityService.registerActivity(signupInfo);
    }


    @Operation(summary = "获取报名列表", description = "获得用户所有报名活动列表")
    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Activity>> getRegisterList(@Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.getRegisterList(openid);
    }
}
