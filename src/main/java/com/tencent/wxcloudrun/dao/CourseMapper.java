package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Course;
import com.tencent.wxcloudrun.model.Department;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CourseMapper{
    void save(Comment comment);
    List<Course> getCourseList(Integer departmentID);

    List<Course> getSearchCourseList();

    List<Course> getCourse(ArrayList<String> courseID);

    List<Department> getDepartmentList();
    
    void get_zan_1(String openid, Integer commentID, short zan);

    User get_user(String openid);

    void create_zan_1(String openid, Integer commentID, short zan);

    Comment get_comment(Integer commentID);

    void create_post_1(Integer commentID, String report);


    void get_post(Integer commentID, String report);

    void disable_comment(Integer commentID);

    void create_zan_2(String openid, String commentID, short zan);

    void create_zan_3(String openid, Integer commentID, short zan);

    void get_zan_2(String openid, String commentID, short zan);
}

