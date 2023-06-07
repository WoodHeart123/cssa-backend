package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CourseMapper {
    void saveComment(CourseComment courseComment);

    void updateCourse(Course course);

    /**
     * @param offset
     * @param limit
     * @param field
     * @param order
     * @return
     */
    List<Course> getAllCourseList(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                  @Param("field") String field, @Param("order") String order,
                                  @Param("commentCount") Integer commentCount, @Param("isGrad") Boolean isGrad);


    /**
     * @param departmentID department ID
     * @param field        field that need to be sorted
     * @param order        ASC/DESC
     * @return list of course under department with given order
     */
    List<Course> getCourseList(@Param("departmentID") Integer departmentID, @Param("field") String field,
                               @Param("order") String order, @Param("isGrad") Boolean isGrad);

    /**
     * get list of course provided by search db
     *
     * @param courseID list of course ID
     * @return list of course
     */
    List<Course> getCourse(ArrayList<String> courseID);

    /**
     * a backup method if redis failed
     *
     * @return list of course
     */
    List<Course> searchCourse(String departmentAbrev, String courseNum);

    List<Department> getDepartmentList();

    /**
     * Set likelist of comments of users
     *
     * @param user user information
     */
    void setLikeList(User user);

    /**
     * increase likecount of comment by one
     *
     * @param commentID ID of comment
     */
    void incrementCount(Integer commentID);

    /**
     * Get user information
     *
     * @param userID ID of user
     * @return user information
     */
    User getUser(String userID);

    /**
     * Get comment information
     *
     * @param commentID ID of comment
     * @return comment information
     */
    CourseComment getComment(Integer commentID);

    /**
     * Add report list into a comment
     *
     * @param courseComment comment information
     */
    void addReportList(CourseComment courseComment);

    /**
     * hide a comment
     *
     * @param commentID ID of comment
     */
    void hideComment(Integer commentID);

    List<CourseComment> getCommentList(@Param("courseID") Integer courseID, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("orderField") String orderField);

    Integer getPostCommentCount(String userID, Integer courseID);

    void postFile(CourseFile courseFile);


}

