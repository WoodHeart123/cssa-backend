package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CourseService {
    Response<Object> postComment(CourseComment courseComment);
    Response<List<Course>> getCourseList(Integer departmentID, Integer offset, Integer limit, SortType sortType, Boolean isGrad);
    Response<List<Department>> getDepartmentList();
    Response<Object> zan(String userID, Integer commentID);
    Response<List<Course>> getCourse(ArrayList<String> courseID);
    Response<List<CourseComment>> getCommentList(Integer courseID, Integer offset, Integer limit, SortType sortType);
    Response<List<Course>> searchCourse(String query);

    Response<Object> postFile(CourseFile courseFile);
}
