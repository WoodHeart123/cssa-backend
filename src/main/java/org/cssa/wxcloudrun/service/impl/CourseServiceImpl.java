package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.CourseMapper;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.CourseService;
import com.alibaba.fastjson2.JSON;
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
     *
     * @param courseComment the comment
     * @return success Response information if everything is recorded in Database correctly
     */
    @Override
    @Transactional
    public Response<Object> postComment(CourseComment courseComment) {
        if (courseComment.getUserAvatar() == null) {
            Random random = new Random();
            courseComment.setUserAvatar(random.nextInt(10) + 1);
        }
        // to check if there is already two comments
        Integer count = courseMapper.getPostCommentCount(courseComment.getUserID(), courseComment.getCourseID());
        if (count >= 2) {
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
        course.setAvgDifficulty((course.getAvgDifficulty() * course.getCommentCount() + courseComment.getDifficulty()) / (course.getCommentCount() + 1));
        course.setAvgPrefer((course.getAvgPrefer() * course.getCommentCount() + courseComment.getPrefer()) / (course.getCommentCount() + 1));
        course.setCommentCount(course.getCommentCount() + 1);
        courseMapper.updateCourse(course);
        return Response.builder().message("成功").status(100).build();
    }

    /**
     * @param departmentID if departmentID == 0, return all course, else return course in given department
     * @return list of course matched given department
     */
    @Override
    public Response<List<Course>> getCourseList(Integer departmentID, Integer offset, Integer limit, SortType sortType, Boolean isGrad) {
        if (departmentID.equals(0)) {
            return new Response<>(courseMapper.getAllCourseList(offset, limit, sortType.getField(), sortType.getOrder(), sortType.getCommentCount(), isGrad));
        }
        return new Response<>(courseMapper.getCourseList(departmentID, sortType.getField(), sortType.getOrder(), isGrad));
    }

    /**
     * @return list of department
     */
    @Override
    public Response<List<Department>> getDepartmentList() {
        return new Response<>(courseMapper.getDepartmentList());
    }

    /**
     * Service to do what Zan controller want to request
     *
     * @param commentID ID of comment
     * @return success Response information if everything is recorded in Database correctly
     */
    @Override
    @Transactional
    public Response<Object> like(Integer commentID) {
        courseMapper.incrementCount(commentID);
        return Response.builder().status(100).message("成功").build();
    }


    @Override
    public Response<List<CourseComment>> getCommentList(Integer courseID, Integer offset, Integer limit, SortType sortType) {
        List<CourseComment> courseCommentList = courseMapper.getCommentList(courseID, offset, limit, sortType.getField());
        return new Response<>(courseCommentList);
    }

    @Override
    public Response<List<Course>> searchCourse(String query) {
        int i;
        for (i = 0; i < query.length(); i++) {
            if (query.charAt(i) >= '0' && query.charAt(i) <= '9') {
                break;
            }
        }
        return new Response<>(courseMapper.searchCourse(query.substring(0, i) + "%", query.substring(i) + "%"));
    }

    @Override
    @Transactional
    public Response<Object> updateComment(Integer userID, Integer commentID, String comment) {
        courseMapper.updateComment(userID, commentID, comment);
        return new Response<>();
    }

    @Override
    public Response<List<CourseComment>> getUserComment(Integer userID, Integer offset, Integer limit) {
        return new Response<>(courseMapper.getUserComment(userID, offset, limit));
    }

    @Override
    public Response<Object> deleteComment(Integer userID, Integer commentID) {
        courseMapper.deleteComment(userID, commentID);
        return new Response<>();
    }

}
