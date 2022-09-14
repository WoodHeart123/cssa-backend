package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Comment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@CrossOrigin
@RequestMapping({"/admin"})
public class CourseController {

    @Resource
    CourseMapper courseMapper;

    @PostMapping("/postcomment")
    public String save(@RequestBody Comment comment) {
        CourseMapper.save(comment);
        return "提交成功！";
    }
}
