package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping({"/admin"})
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    Jwtutil jwtutil;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value={"/login"}, method={RequestMethod.POST})
    Response login(@RequestBody Admin admin, HttpServletRequest request){
        return adminService.login(admin);
    }

    @RequestMapping(value = {"/register"},method={RequestMethod.POST})
    Response register(@RequestBody Admin admin){
        return adminService.register(admin);
    }

    @RequestMapping(value={"/getActivityList"},method={RequestMethod.GET})
    Response getActivityList(){
        return adminService.getActivityList();
    }

    @RequestMapping(value={"/createActivity"}, method={RequestMethod.POST})
    public Response createActivity(@RequestBody Activity activity){
        return adminService.createActivity(activity);
    }

    @RequestMapping(value={"/activitySignup"}, method={RequestMethod.GET})
    public Response getActivitySignup(@RequestParam String actID){
        return adminService.getActivitySignup(actID);
    }

    @RequestMapping(value={"/deleteActivity"}, method={RequestMethod.GET})
    public Response deleteActivity(@RequestParam String actID){
        return adminService.deleteActivity(actID);
    }

    @RequestMapping(value={"/postMainPagePhoto"}, method={RequestMethod.POST})
    public Response postMainPagePhoto(@RequestBody MainPagePhoto mainPagePhoto){
        return adminService.postMainPagePhoto(mainPagePhoto);
    }

    @RequestMapping(value={"deleteMainPagePhoto"}, method={RequestMethod.GET})
    public Response deleteMainPagePhoto(@RequestParam String photoID, HttpServletRequest request){
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
        if(token.isEmpty() || !jwtutil.isTokenValid(token.get())){
            return Response.builder().status(310).message("无管理员信息").build();
        }
        return adminService.deleteMainPagePhoto(photoID);
    }

    @RequestMapping(value={"getMainPagePhotoList"},method={RequestMethod.GET})
    Response getMainPagePhotoList(){
        return adminService.getMainPagePhotoList();
    }


    /**
     * @return list of department
     */
    @RequestMapping(value={"getDepartmentList"}, method={RequestMethod.GET})
    public Response getDepartmentList(HttpServletRequest request){
        return adminService.getDepartmentList();
    }

    @RequestMapping(value={"getCourseList"}, method={RequestMethod.GET})
    public Response getCourseList(@RequestParam Integer departmentID){
        return adminService.getCourseList(departmentID);
    }

    @RequestMapping(value={"getCommentList"}, method={RequestMethod.GET})
    public Response getCommentList(@RequestParam Integer courseID){
        return adminService.getCommentList(courseID);
    }
    @RequestMapping(value={"deleteComment"}, method={RequestMethod.GET})
    public Response deleteComment(@RequestParam Integer commentID){
        return adminService.deleteComment(commentID);
    }
}
