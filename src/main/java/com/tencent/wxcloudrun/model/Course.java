package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Integer courseID;
    private String courseName;
    private Integer courseNum;
    private String departmentAbrev;
    private Integer departmentID;
    private Double avgDifficulty;
    private String credit;
    private Double avgPrefer;
    private Double commentCount;
    private List<Comment> commentList;
}
