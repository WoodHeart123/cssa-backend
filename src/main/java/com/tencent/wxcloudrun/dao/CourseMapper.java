package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> getCourseList(Integer departmentID);
}
