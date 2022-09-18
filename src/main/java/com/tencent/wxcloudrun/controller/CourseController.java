package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping({"/course"})
public class CourseController {

    @Autowired
    CourseService courseService;
    
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
        return courseService.save(comment);

    }
    
    @RequestMapping(value={ "/courselist"}, method = {RequestMethod.GET})
    public Response getCourseList(@RequestParam Optional<Integer> departmentID, HttpServletRequest request){
        if(departmentID.isEmpty()){
            return Response.builder().message("部门ID为空").status(501).build();
        }
        return courseService.getCourseList(departmentID.get());
    }
}
