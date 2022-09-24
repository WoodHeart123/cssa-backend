package com.tencent.wxcloudrun.controller;

import com.github.twohou.sonic.ChannelFactory;
import com.github.twohou.sonic.IngestChannel;
import com.github.twohou.sonic.SearchChannel;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
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
    private CourseService courseService;

    private static ChannelFactory factory = new ChannelFactory("47.97.183.103",1491,"SecretPassword",2000,2000);


    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, HttpServletRequest request) throws IOException {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        SearchChannel search = factory.newSearchChannel();
        search.ping();
        return courseService.getCourse(search.query("course","default", value));

    }

    @PostMapping("/postcomment")
    public Response save(@RequestBody Comment comment, HttpServletRequest request) {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        if(comment.getComment().length() > 300){
            return Response.builder().status(102).message("超过字数限制").build();
        }
        comment.setUserID(openid.get());
        return courseService.postComment(comment);

    }
    
    @RequestMapping(value={ "/courselist"}, method = {RequestMethod.GET})
    public Response getCourseList(@RequestParam Optional<Integer> departmentID, HttpServletRequest request) {
        if (departmentID.isEmpty()) {
            return Response.builder().message("部门ID为空").status(501).build();
        }
        return courseService.getCourseList(departmentID.get());
    }
    
    @RequestMapping(value={ "/departmentlist"}, method = {RequestMethod.GET})
    public Response getDepartmentList(HttpServletRequest request) {
        return courseService.getDepartmentList();
    }
    
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
