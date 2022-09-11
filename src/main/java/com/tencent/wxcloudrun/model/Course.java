package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Integer courseID;
    private String courseName;
    private Integer departmentID;
    private Double avgDifficulty;
    private String credit;
    private Double avgLike;
    private Double commentCount;
    private List<Comment> commentList;
}
