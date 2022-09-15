package com.tencent.wxcloudrun.service.impl;


import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Course;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;

    @Override
    public Response getCourseList(Integer departmentID) {
        return Response.builder().data(courseMapper.getCourseList(departmentID)).status(100).message("成功").build();
    }
}
