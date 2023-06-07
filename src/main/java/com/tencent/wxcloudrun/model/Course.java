package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class Course {
    @Schema(description = "课程ID", example = "1")
    private Integer courseID;
    @Schema(description = "课程名称", example = "Computer Science 577")
    private String courseName;
    @Schema(description = "课程号", example = "305")
    private Integer courseNum;
    @Schema(description = "院系简称", example = "CS")
    private String departmentAbrev;
    @Schema(description = "院系ID", example = "19")
    private Integer departmentID;
    @Schema(description = "平均难度", example = "3.78")
    private Double avgDifficulty;
    @Schema(description = "学分", example = "3")
    private String credit;
    @Schema(description = "平均推荐度", example = "4.5")
    private Double avgPrefer;
    @Schema(description = "评论数量", example = "37800")
    private Double commentCount;
    @Schema(description = "课程评论列表")
    private List<CourseComment> courseCommentList;
}
