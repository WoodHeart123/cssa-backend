package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.OrderType;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {
    Response postComment(Comment comment);
    Response getCourseList(Integer departmentID);
    Response getDepartmentList();

    Response zan(String userID, Integer commentID);

    Response report(Integer commentID, String report);

    Response getCourse(ArrayList<String> courseID);

    Response getCommentList(Integer courseID, Integer offset, Integer limit, OrderType orderType);
}
