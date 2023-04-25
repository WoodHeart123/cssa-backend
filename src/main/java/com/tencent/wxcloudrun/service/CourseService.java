package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.CourseComment;
import com.tencent.wxcloudrun.model.CourseFile;
import com.tencent.wxcloudrun.model.SortType;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {
    Response postComment(CourseComment courseComment);
    Response getCourseList(Integer departmentID, Integer offset, Integer limit, SortType sortType, Boolean isGrad);
    Response getDepartmentList();
    Response zan(String userID, Integer commentID);
    Response report(Integer commentID, String report);
    Response getCourse(ArrayList<String> courseID);
    Response getCommentList(Integer courseID, Integer offset, Integer limit, SortType sortType);
    Response searchCourse(String query);

    Response postFile(CourseFile courseFile);
}
