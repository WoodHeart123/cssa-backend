package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class CourseFile {
    private Integer fileID;
    private Integer courseID;
    private String userID;
    private String path;
    private String name;
    private Integer size;
}
