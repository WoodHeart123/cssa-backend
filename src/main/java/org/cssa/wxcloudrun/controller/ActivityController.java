package org.cssa.wxcloudrun.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.cssa.wxcloudrun.service.ActivityService;
import org.cssa.wxcloudrun.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/activity"})
@Tag(name = "活动中心")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    EmailService emailService;

    @Operation(summary = "查看用户报名信息", description = "查看用户报名信息")
    @RequestMapping(value = {"/events/signup/{act_id}"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<SignupInfo> checkSignup(@Parameter(description = "活动ID", required = true)
                                                @PathVariable(name = "act_id") Integer actID,
                                                @Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
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

    @Operation(summary = "取消报名", description = "取消用户的报名信息")
    @RequestMapping(value = {"/register/{act_id}"}, method = {RequestMethod.DELETE}, produces = "application/json")
    public Response<Object> cancelRegister(@Parameter(name = "活动ID", required = true) @PathVariable(name = "act_id") Integer actID,
                                           @Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.cancelRegister(openid, actID);
    }


    @Operation(summary = "获取报名列表", description = "获得用户所有报名活动列表")
    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Activity>> getRegisterList(@Parameter(description = "微信ID", required = true) @RequestHeader("x-wx-openid") String openid) {
        return activityService.getRegisterList(openid);
    }

    @Operation(summary = "获取单个活动详情", description = "根据给定的event_id获取具体的活动详情。")
    @GetMapping("/activityList/{event_id}")
    public Response<Activity> getActivityDetails(
            @Parameter(description = "活动ID", required = true) @PathVariable(name="event_id") Integer eventId) {
        return activityService.getActivityDetails(eventId);
    }

    @Operation(summary = "获取所有未结束的活动详情", description = "获取所有结束日期大于当前日期的活动详情")
    @GetMapping("/ongoingActivities")
    public Response<List<Activity>> getOngoingActivities() {
        return activityService.getOngoingActivities();
    }

    @Operation(summary = "更新活动", description = "更新给定event_id的活动信息。")
    @PutMapping("/activityList/{event_id}")
    public Response<String> updateActivity(
            @Parameter(description = "活动ID", required = true) @PathVariable(name="event_id") Integer eventId,
            @RequestBody Activity updatedActivity) {
        return activityService.updateFullActivity(eventId, updatedActivity);
    }

    @Operation(summary = "删除活动", description = "删除给定event_id的活动。")
    @DeleteMapping("/activityList/{event_id}")
    public Response<String> deleteActivity(
            @Parameter(description = "活动ID", required = true) @PathVariable(name="event_id") Integer eventId) {
        return activityService.deleteActivity(eventId);
    }
}