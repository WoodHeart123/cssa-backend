package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.model.Comment;
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
        return courseService.get_zan(openid.get(), commentID, zan);
    }

    @RequestMapping(value = {"/report"}, method = {RequestMethod.POST})
    public Response Post(@RequestBody Comment comment, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        comment.setReportList(JSON.parseArray(comment.getReportListJSON(), String.class));
        return courseService.get_post(comment.getCommentID(), comment.getReportList().get(0));
    }
}
