package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/admin"})
@Api(tags = "管理员接口")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminService adminService;
    @Autowired
    Jwtutil jwtutil;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @Operation(summary = "管理员登录", description = "管理员登录")
    public Response<Admin> login(@Parameter(description = "管理员的用户名和密码") @RequestBody @Valid Admin admin) {
        return adminService.login(admin);
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    @Operation(summary = "管理员注册", description = "管理员注册")
    public Response<Object> register(@Parameter(description = "管理员注册密码和用户名") @RequestBody @Valid Admin admin) {
        return adminService.register(admin);
    }

    @Operation(summary = "获取活动列表", description = "获取所有活动列表")
    @RequestMapping(value = {"/getActivityList"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Activity>> getActivityList() {
        return adminService.getActivityList();
    }

    @RequestMapping(value = {"/createActivity"}, method = {RequestMethod.POST})
    @Operation(summary = "创建活动", description = "创建活动")
    public Response<Object> createActivity(@Parameter(description = "活动信息") @RequestBody @Valid Activity activity) {
        return adminService.createActivity(activity);
    }

    @RequestMapping(value = {"/activitySignup"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取报名信息", description = "获取所有用户当前活动的报名信息")
    public Response<List<SignupInfo>> getActivitySignup(@Parameter(description = "活动ID") @RequestParam String actID) {
        return adminService.getActivitySignup(actID);
    }

    @RequestMapping(value = {"/deleteActivity"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "删除活动", description = "删除活动")
    public Response<Object> deleteActivity(@Parameter(description = "活动ID") @RequestParam String actID) {
        return adminService.deleteActivity(actID);
    }

    @RequestMapping(value = {"/postMainPagePhoto"}, method = {RequestMethod.POST})
    @Operation(summary = "发布主页大图", description = "发布主页大图")
    public Response<Object> postMainPagePhoto(@Parameter(description = "大图信息") @RequestBody @Valid MainPagePhoto mainPagePhoto) {
        return adminService.postMainPagePhoto(mainPagePhoto);
    }

    @RequestMapping(value = {"deleteMainPagePhoto"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "删除主页大图", description = "删除主页大图")
    public Response<Object> deleteMainPagePhoto(@Parameter(description = "主页大图ID") @RequestParam String photoID) {
        return adminService.deleteMainPagePhoto(photoID);
    }

    @RequestMapping(value = {"getMainPagePhotoList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取主页大图列表", description = "获取主页大图列表")
    public Response<List<MainPagePhoto>> getMainPagePhotoList() {
        return adminService.getMainPagePhotoList();
    }

    @RequestMapping(value = {"getDepartmentList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取院系列表", description = "获取院系列表")
    public Response<List<Department>> getDepartmentList() {
        return adminService.getDepartmentList();
    }

    @RequestMapping(value = {"getCourseList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取课程列表", description = "获取对应院系课程列表")
    public Response<List<Course>> getCourseList(@Parameter(description = "院系ID") @RequestParam Integer departmentID) {
        return adminService.getCourseList(departmentID);
    }

    @RequestMapping(value = {"getCommentList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取课程评论列表", description = "获取对应课程评论列表")
    public Response<List<CourseComment>> getCommentList(@Parameter(description = "课程ID") @RequestParam Integer courseID) {
        return adminService.getCommentList(courseID);
    }

    @RequestMapping(value = {"deleteComment"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "删除课程评论", description = "删除课程评论")
    public Response<Object> deleteComment(@Parameter(description = "评论ID") @RequestParam Integer commentID) {
        return adminService.deleteComment(commentID);
    }
}
