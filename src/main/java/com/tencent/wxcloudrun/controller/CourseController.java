package com.tencent.wxcloudrun.controller;

import com.github.twohou.sonic.ChannelFactory;
import com.github.twohou.sonic.SearchChannel;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.SortType;
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


    /**
     *
     * @param value
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, HttpServletRequest request) throws IOException {
        SearchChannel search = channelFactory.newSearchChannel();
        search.ping();
        return courseService.getCourse(search.query("course","default", value));

    }
    /**
     * Add a newly posted comment to database
     * @param comment comment which is posted
     * @param request Header of this Request
     * @return Add comment content and related information to database
     */
    @PostMapping("/postcomment")
    public Response save(@RequestBody Comment comment, HttpServletRequest request) {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-unionid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        if(comment.getComment().length() > 300){
            return Response.builder().status(103).message("超过字数限制").build();
        }
        comment.setUserID(openid.get());
        return courseService.postComment(comment);

    }

    @RequestMapping(value={"/getCommentList"}, method = {RequestMethod.GET})
    public Response getCommentList(@RequestParam Integer courseID, @RequestParam Integer offset, @RequestParam Integer limit, @RequestParam SortType order, HttpServletRequest request){
        return courseService.getCommentList(courseID,offset,limit,order);
    }
    
    @RequestMapping(value={"/courselist"}, method = {RequestMethod.GET})
    public Response getCourseList(@RequestParam Optional<Integer> departmentID, @RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit, @RequestParam Optional<SortType> orderType, HttpServletRequest request) {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-unionid"));
        if (departmentID.isEmpty()) {
            return Response.builder().message("部门ID为空").status(104).build();
        }
        if (departmentID.get().equals(0) && (offset.isEmpty() || limit.isEmpty() || orderType.isEmpty())){
            return Response.builder().message("缺少参数").status(105).build();
        }
        if(orderType.isEmpty()){
            return Response.builder().message("缺少参数").status(105).build();
        }
        return courseService.getCourseList(departmentID.get(),offset.get(),limit.get(),orderType.get());
    }
    
    @RequestMapping(value={ "/departmentlist"}, method = {RequestMethod.GET})
    public Response getDepartmentList() {
        return courseService.getDepartmentList();
    }

    /**
     * This controller record which comment users give zan, and increase the number of zan in this comment by one
     * Request path is /zan, and method is GET
     * @param commentID ID of comment
     * @param request Header of this request(for getting wx-openid)
     * @return Response information and data
     */
    @RequestMapping(value = {"/zan"}, method = {RequestMethod.GET})
    public Response Zan(@RequestParam(name="commentID") Integer commentID, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-unionid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.zan(openid.get(), commentID);
    }

    /**
     * Report a bad comment, and record the number of reports of this comment
     * @param comment comment which is reported
     * @param request Header of this Request
     * @return Response information and data
     */
    @RequestMapping(value = {"/report"}, method = {RequestMethod.POST})
    public Response Post(@RequestBody Comment comment, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-unionid"));
        if(openid.isEmpty()) {
            return Response.builder().status(102).message("无用户信息").build();
        }
        return courseService.report(comment.getCommentID(), comment.getReportList().get(0));
    }
}
