package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface CourseMapper{
    void save(Comment comment);
    List<Course> getCourseList(Integer departmentID);
}

