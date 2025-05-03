package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cssa.wxcloudrun.model.Course;
import org.cssa.wxcloudrun.model.CourseComment;
import org.cssa.wxcloudrun.model.Department;
import org.cssa.wxcloudrun.model.User;

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
     * increase likecount of comment by one
     *
     * @param commentID ID of comment
     */
    void incrementCount(Integer commentID);

    List<CourseComment> getCommentList(@Param("courseID") Integer courseID, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("orderField") String orderField);

    Integer getPostCommentCount(Integer userID, Integer courseID);

    /**
     * @param userID user ID
     * @param offset the starting commentID
     * @param limit  number of comments
     * @return list of comment with limit size
     */
    List<CourseComment> getUserComment(@Param("userID") Integer userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void updateComment(Integer userID, Integer commentID, String comment);

    void deleteComment(Integer userID, Integer commentID);


}

