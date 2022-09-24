package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {
    Response postComment(Comment comment);
    Response getCourseList(Integer departmentID);
    Response getDepartmentList();

    Response get_zan(String openid, Integer commentID, short zan);

    Response get_post(Integer commentID, String report);

    Response getCourse(ArrayList<String> courseID);
}
