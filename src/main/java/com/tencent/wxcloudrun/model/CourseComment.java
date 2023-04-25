package com.tencent.wxcloudrun.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;

import java.sql.Timestamp;
import java.util.List;

/**
 * 评论类
 */
@Data
public class CourseComment {

    private String userID;
    private Integer userAvatar;
    private Integer courseID;
    private String courseName;
    private Integer courseNum;
    private String departmentAbrev;
    private Integer commentID;
    //教授名
    private String professor;
    //上课时间 示例：2022Spring
    private String courseTime;
    //难度 1-5
    private Double difficulty;
    //喜爱值 1-5
    private Double prefer;
    private String comment;
    private Integer likeCount;
    // 举报信息列表
    private List<String> reportList;
    private String reportListJSON;
    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp commentTime;

}