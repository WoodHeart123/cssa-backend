package com.tencent.wxcloudrun.service.impl;


import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;
    @Override
    public Response save(Comment comment) {
        courseMapper.save(comment);
        return Response.builder().message("成功").status(100).build();
    }
}
