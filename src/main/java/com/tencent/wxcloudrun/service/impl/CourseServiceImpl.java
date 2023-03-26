package com.tencent.wxcloudrun.service.impl;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.CourseMapper;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CourseServiceImpl implements CourseService {



    @Autowired
    private CourseMapper courseMapper;

    /**
     * Service to do what postComment controller want to request
     * @param courseComment the comment
     * @return success Response information if everything is recorded in Database correctly
     */
    @Override
    @Transactional
    public Response postComment(CourseComment courseComment) {
        if(courseComment.getUserAvatar() == null){
            Random random = new Random();
            courseComment.setUserAvatar(random.nextInt(10) + 1);
        }
        // to check if there is already two comments
        Integer count = courseMapper.getPostCommentCount(courseComment.getUserID(), courseComment.getCourseID());
        if(count >= 2){
            return Response.builder().status(110).message("评论数超过两个").build();
        }
        // save the comment
        courseComment.setCommentTime(new Timestamp(new Date().getTime()));
        courseMapper.saveComment(courseComment);
        // update course stats
        ArrayList<String> courseIDList = new ArrayList<>(1);
        courseIDList.add(courseComment.getCourseID().toString());
        List<Course> courseList = courseMapper.getCourse(courseIDList);
        Course course = courseList.get(0);
        course.setAvgDifficulty((course.getAvgDifficulty() * course.getCommentCount() + courseComment.getDifficulty())/(course.getCommentCount() + 1));
        course.setAvgPrefer((course.getAvgPrefer() * course.getCommentCount() + courseComment.getPrefer())/(course.getCommentCount() + 1));
        course.setCommentCount(course.getCommentCount() + 1);
        courseMapper.updateCourse(course);
        return Response.builder().message("成功").status(100).build();
    }

    /**
     * @param departmentID if departmentID == 0, return all course, else return course in given department
     * @return list of course matched given department
     */
    @Override
    public Response getCourseList(Integer departmentID, Integer offset, Integer limit, SortType sortType) {
        if(departmentID.equals(0)){
            return Response.builder().data(courseMapper.getAllCourseList(offset,limit, sortType.getField(), sortType.getOrder(),sortType.getCommentCount())).status(100).message("成功").build();
        }
        return Response.builder().data(courseMapper.getCourseList(departmentID, sortType.getField(), sortType.getOrder())).status(100).message("成功").build();
    }

    /**
     * @return list of department
     */
    @Override
    public Response getDepartmentList() {
        return Response.builder().data(courseMapper.getDepartmentList()).status(100).message("成功").build();
    }

    /**
     * Service to do what Zan controller want to request
     * @param userID wx-openid
     * @param commentID ID of comment
     * @return success Response information if everything is recorded in Database correctly
     * else error Response or 已点赞
     */
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
            return Response.builder().status(107).message("已点赞").build();
        }
        user.getLikedComment().add(commentID);
        user.setLikedCommentJSON(JSON.toJSONString(user.getLikedComment()));
        courseMapper.setLikeList(user);
        courseMapper.incrementCount(commentID);
        return Response.builder().status(100).message("成功").build();
    }

    /**
     * Service to do what Post controller want to request
     * @param commentID ID of comment
     * @param report Feedback of this comment
     * @return success Response information if report is recorded in Database correctly
     */
    @Override
    @Transactional
    public Response report(Integer commentID, String report) {
        CourseComment courseComment = courseMapper.getComment(commentID);
        if(courseComment.getReportListJSON() == null){
            courseComment.setReportList(new ArrayList<>());
        }else{
            courseComment.setReportList(JSON.parseArray(courseComment.getReportListJSON(), String.class));
        }
        courseComment.getReportList().add(report);
        courseComment.setReportListJSON(JSON.toJSONString(courseComment.getReportList()));
        courseMapper.addReportList(courseComment);
        if(courseComment.getReportList().size() == 10) {
            courseMapper.hideComment(commentID);
        }
        return Response.builder().status(100).message("成功").build();
    }

    @Override
    public Response getCourse(ArrayList<String> courseID) {
        return Response.builder().data(courseMapper.getCourse(courseID)).status(100).build();
    }

    @Override
    public Response getCommentList(Integer courseID, Integer offset, Integer limit, SortType sortType) {
        List<CourseComment> courseCommentList;
        courseCommentList = courseMapper.getCommentList(courseID, offset, limit, sortType.getField());
        return Response.builder().data(courseCommentList).status(100).message("成功").build();
    }

    @Override
    public Response searchCourse(String query) {
        int i;
        for(i = 0;i < query.length();i++){
            if(query.charAt(i) >= '0' && query.charAt(i) <= '9'){
                break;
            }
        }
        return Response.builder().data(courseMapper.searchCourse(query.substring(0,i) + "%",  query.substring(i) + "%")).build();
    }
}
