package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Info;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.Course;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    
    @Override
    @Transactional
    public Response postComment(Comment comment) {
        courseMapper.saveComment(comment);
        ArrayList<String> courseIDList = new ArrayList<String>(1);
        courseIDList.add(comment.getCourseID().toString());
        List<Course> courseList = courseMapper.getCourse(courseIDList);
        Course course = courseList.get(0);
        course.setAvgDifficulty(course.getAvgDifficulty() * course.getCommentCount() + comment.getDifficulty() /(course.getCommentCount() + 1));
        course.setAvgLike(course.getAvgLike() * course.getCommentCount() + comment.getPrefer()/(course.getCommentCount() + 1));
        course.setCommentCount(course.getCommentCount() + 1);
        courseMapper.updateCourse(course);
        return Response.builder().message("成功").status(100).build();
    }

    @Override
    public Response getCourseList(Integer departmentID) {
        return Response.builder().data(courseMapper.getCourseList(departmentID)).status(100).message("成功").build();
    }
    
    @Override
    public Response getDepartmentList() {
        return Response.builder().data(courseMapper.getDepartmentList()).status(100).message("成功").build();
    }
    
    @Override
    public Response get_zan(String openid, Integer commentID, short zan) {
        User user = courseMapper.get_user(openid);
        if(user.getLikedCommentJSON() == null){
            try{
                courseMapper.create_zan_1(openid,commentID, zan);
                courseMapper.create_zan_2(openid, String.valueOf(commentID),zan);
                courseMapper.create_zan_3(openid,commentID,zan);
                return Response.builder().status(100).message("成功").build();
            }catch (Exception exception){
                return Response.builder().status(101).message(exception.getMessage()).build();
            }
        }
        user.setLikedComment(JSON.parseArray(user.getLikedCommentJSON(), Integer.class));
        if(user.getLikedComment().contains(commentID)){
            return Response.builder().status(500).message("已点赞").build();
        }
        try{
            courseMapper.get_zan_1(openid,commentID, zan);
            courseMapper.get_zan_2(openid,String.valueOf(commentID), zan);
            return Response.builder().status(100).message("成功").build();
        }catch (Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response get_post(Integer commentID, String report) {
        report = "\"" + report + "\"";
        Comment comment = courseMapper.get_comment(commentID);
        if(comment.getReportListJSON() == null){
            try{
                courseMapper.create_post_1(commentID, report);
                //courseMapper.create_post_2(commentID, report);
                return Response.builder().status(100).message("成功").build();
            }catch (Exception exception){
                return Response.builder().status(101).message(exception.getMessage()).build();
            }
        }
        comment.setReportList(JSON.parseArray(comment.getReportListJSON(), String.class));
        try{
            courseMapper.get_post(commentID, report);
            if(comment.getReportList().size() >= 9){
                courseMapper.disable_comment(commentID);
            }
            return Response.builder().status(100).message("成功").build();
        }catch (Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response getCourse(ArrayList<String> courseID) {
        return Response.builder().data(courseMapper.getCourse(courseID)).status(100).build();
    }
}
