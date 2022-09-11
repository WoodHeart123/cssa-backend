package com.tencent.wxcloudrun.model;


import lombok.Data;

import java.util.List;

@Data
public class Department {
    private String department;
    private String departmentAbrev;
    private Integer departmentID;
    private List<Course> courseList;
}
