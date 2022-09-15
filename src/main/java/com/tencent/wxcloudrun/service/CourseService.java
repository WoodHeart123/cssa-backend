package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

    Response getCourseList(Integer departmentID);
}
