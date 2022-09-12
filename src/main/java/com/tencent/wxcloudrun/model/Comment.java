package com.tencent.wxcloudrun.model;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private Integer courseID;
    private Integer commentID;
    private String professor;
    private String courseTime;
    private Double difficulty;
    private Double prefer;
    private String comment;
    private Integer zanCount;
    private Integer caiCount;
    private Integer reportCount;
    private Boolean visible;
    private SortType sortType;
    private Timestamp commentTime;
}
