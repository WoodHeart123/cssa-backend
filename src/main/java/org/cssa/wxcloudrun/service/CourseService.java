package org.cssa.wxcloudrun.service;


import org.cssa.wxcloudrun.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CourseService {
    Response<Object> postComment(CourseComment courseComment);

    Response<List<Course>> getCourseList(Integer departmentID, Integer offset, Integer limit, SortType sortType, Boolean isGrad);

    Response<List<Department>> getDepartmentList();

    Response<Object> like(Integer commentID);

    Response<List<CourseComment>> getCommentList(Integer courseID, Integer offset, Integer limit, SortType sortType);

    Response<List<Course>> searchCourse(String query);

    Response<Object> updateComment(Integer userID, Integer commentID, String comment);

    Response<List<CourseComment>> getUserComment(Integer userID, Integer offset, Integer limit);

    Response<Object> deleteComment(Integer userID, Integer commentID);


}
