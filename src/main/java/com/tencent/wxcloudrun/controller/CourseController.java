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
        comment.setUserID(openid.get());
        return courseService.save(comment);
    }
}
