package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CourseFile {
    @Schema(description = "文件ID", example = "1")
    private Integer fileID;
    @Schema(description = "课程ID", example = "29")
    private Integer courseID;
    @Schema(description = "用户ID", example = "edg50-dsr2")
    private String userID;
    @Schema(description = "文件路径", example = "https://xxx.com/xxx.jpg")
    private String path;
    @Schema(description = "文件名", example = "xxx.jpg")
    private String name;
    @Schema(description = "文件大小", example = "1024")
    private Integer size;
}
