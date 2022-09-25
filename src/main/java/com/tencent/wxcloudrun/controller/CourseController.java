package com.tencent.wxcloudrun.controller;

import com.github.twohou.sonic.ChannelFactory;
import com.github.twohou.sonic.SearchChannel;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.OrderType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;



@RestController
@CrossOrigin
@RequestMapping({"/course"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChannelFactory channelFactory;


    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, HttpServletRequest request) throws IOException {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        SearchChannel search = channelFactory.newSearchChannel();
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

    @RequestMapping(value={"/getCommentList"}, method = {RequestMethod.GET})
    public Response getCommentList(@RequestParam Integer courseID, @RequestParam Integer offset, @RequestParam Integer limit, @RequestParam OrderType order, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.getCommentList(courseID,offset,limit,order);
    }
    
    @RequestMapping(value={"/courselist"}, method = {RequestMethod.GET})
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
    public Response Zan(@RequestParam(name="commentID") Integer commentID, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.zan(openid.get(), commentID);
    }

    @RequestMapping(value = {"/report"}, method = {RequestMethod.POST})
    public Response Post(@RequestBody Comment comment, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.report(comment.getCommentID(), comment.getReportList().get(0));
    }
}
