package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping({"/course"})
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping(value = {"/zan"}, method = {RequestMethod.GET})
    public Response Zan(@RequestParam(name="commentID") Integer commentID, @RequestParam(name="zan") short zan,
                        HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.get_zan(String.valueOf(openid), commentID, zan);
    }

    @RequestMapping(value = {"/report"}, method = {RequestMethod.POST})
    public Response Post(@RequestBody Integer commentID, @RequestBody List<String> reportList, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.get_post(commentID, reportList.get(0));
    }
}
