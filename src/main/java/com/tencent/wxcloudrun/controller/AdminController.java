package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.AdminService;
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

    @RequestMapping(value={"/login"}, method={RequestMethod.POST})
    Response login(@RequestBody Admin admin, HttpServletRequest request){
        return adminService.login(admin);
    }

    @RequestMapping(value={"getActivityList"},method={RequestMethod.GET})
    Response getActivityList(HttpServletRequest request){
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
        if(token.isEmpty() || !jwtutil.isTokenValid(token.get())){
            return Response.builder().status(110).message("无管理员信息").build();
        }
        return adminService.getActivityList();
    }

    @RequestMapping(value={"/createActivity"}, method={RequestMethod.POST})
    public Response createActivity(@RequestBody Activity activity, HttpServletRequest request){
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
        if(token.isEmpty() || !jwtutil.isTokenValid(token.get())){
            return Response.builder().status(310).message("无管理员信息").build();
        }
        return adminService.createActivity(activity);
    }

    @RequestMapping(value={"activitySignup"}, method={RequestMethod.GET})
    public Response getActivitySignup(@RequestParam String actID, HttpServletRequest request){
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
        if(token.isEmpty() || !jwtutil.isTokenValid(token.get())){
            return Response.builder().status(310).message("无管理员信息").build();
        }
        return adminService.getActivitySignup(actID);
    }

    @RequestMapping(value={"deleteActivity"}, method={RequestMethod.GET})
    public Response deleteActivity(@RequestParam String actID, HttpServletRequest request){
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
        if(token.isEmpty() || !jwtutil.isTokenValid(token.get())){
            return Response.builder().status(310).message("无管理员信息").build();
        }
        return adminService.deleteActivity(actID);
    }

    @RequestMapping(value={"/postMainPagePhoto"}, method={RequestMethod.POST})
    public Response postMainPagePhoto(@RequestBody MainPagePhoto mainPagePhoto, HttpServletRequest request){
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
        if(token.isEmpty() || !jwtutil.isTokenValid(token.get())){
            return Response.builder().status(310).message("无管理员信息").build();
        }
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

}
