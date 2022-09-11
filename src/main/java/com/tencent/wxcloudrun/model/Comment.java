package com.tencent.wxcloudrun.model;


import lombok.Data;

@Data
public class Comment {
    private Integer courseID;
    private Integer commentID;
    private String professor;
    private String time;
    private Double difficulty;
    private Double prefer;
    private String comment;
    private Integer zanCount;
    private Integer caiCount;
    private Integer reportCount;
    private Boolean visible;
}
