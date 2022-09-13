package com.tencent.wxcloudrun.model;


import lombok.Data;

import java.sql.Timestamp;

/**
 * 评论类
 */
@Data
public class Comment {
    private String userID;
    private Integer courseID;
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
    private Integer zanCount;
    private Integer caiCount;
    private Integer reportCount;
    private Boolean visible;
    //评论时间
    private Timestamp commentTime;
}
