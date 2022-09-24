package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.*;
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
        course.setAvgPrefer(course.getAvgPrefer() * course.getCommentCount() + comment.getPrefer()/(course.getCommentCount() + 1));
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
    @Transactional
    public Response zan(String userID, Integer commentID) {
        User user = courseMapper.getUser(userID);
        if(user.getLikedCommentJSON() == null){
            user.setLikedComment(new ArrayList<>());
        }else{
            user.setLikedComment(JSON.parseArray(user.getLikedCommentJSON(), Integer.class));
        }
        if(user.getLikedComment().contains(commentID)){
            return Response.builder().status(180).message("已点赞").build();
        }
        user.getLikedComment().add(commentID);
        user.setLikedCommentJSON(JSON.toJSONString(user.getLikedComment()));
        courseMapper.setLikeList(user);
        courseMapper.incrementCount(commentID);
        return Response.builder().status(100).message("成功").build();
    }

    @Override
    @Transactional
    public Response report(Integer commentID, String report) {
        Comment comment = courseMapper.getComment(commentID);
        if(comment.getReportListJSON() == null){
            comment.setReportList(new ArrayList<>());
        }else{
            comment.setReportList(JSON.parseArray(comment.getReportListJSON(), String.class));
        }
        comment.getReportList().add(report);
        courseMapper.addReportList(comment);
        if(comment.getReportList().size() == 10) {
            courseMapper.hideComment(commentID);
        }
        return Response.builder().status(100).message("成功").build();
    }

    @Override
    public Response getCourse(ArrayList<String> courseID) {
        return Response.builder().data(courseMapper.getCourse(courseID)).status(100).build();
    }

    @Override
    public Response getCommentList(Integer courseID, Integer offset, Integer limit, OrderType orderType) {
        List<Comment> commentList;
        if (orderType == OrderType.SORT_BY_LIKE) {
            commentList = courseMapper.getCommentList(courseID, offset, limit, "likeCount");
        } else {
            commentList = courseMapper.getCommentList(courseID, offset, limit, "commentTime");
        }
        return Response.builder().data(commentList).status(100).message("成功").build();
    }
}
