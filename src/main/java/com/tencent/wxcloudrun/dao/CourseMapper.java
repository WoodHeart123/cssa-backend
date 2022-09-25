package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Course;
import com.tencent.wxcloudrun.model.Department;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CourseMapper{
    void saveComment(Comment comment);

    void updateCourse(Course course);

    List<Course> getCourseList(Integer departmentID);

    List<Course> getCourse(ArrayList<String> courseID);

    List<Department> getDepartmentList();
    
    void setLikeList(User user);

    void incrementCount(Integer commentID);

    User getUser(String userID);

    Comment getComment(Integer commentID);

    void addReportList(Comment comment);

    void hideComment(Integer commentID);

    List<Comment> getCommentList(@Param("commentID")Integer commentID,@Param("offset") Integer offset,@Param("limit") Integer limit,@Param("orderField")String orderField);

}

