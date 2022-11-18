package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.SortType;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {
    Response postComment(Comment comment);
    Response getCourseList(Integer departmentID, Integer offset, Integer limit, SortType sortType);
    Response getDepartmentList();
    Response zan(String userID, Integer commentID);
    Response report(Integer commentID, String report);
    Response getCourse(ArrayList<String> courseID);
    Response getCommentList(Integer courseID, Integer offset, Integer limit, SortType sortType);
    Response searchCourse(String query);
}
