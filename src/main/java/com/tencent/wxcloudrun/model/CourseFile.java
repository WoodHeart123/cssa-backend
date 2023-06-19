package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CourseFile {
    @NotNull(message = "文件ID不为空")
    @Schema(description = "文件ID", example = "1")
    private Integer fileID;
    @NotNull(message = "课程ID不为空")
    @Schema(description = "课程ID", example = "29")
    private Integer courseID;
    @NotBlank(message = "用户ID不为空")
    @Schema(description = "用户ID", example = "edg50-dsr2")
    private String userID;
    @NotBlank(message = "文件路径不为空")
    @Schema(description = "文件路径", example = "https://xxx.com/xxx.jpg")
    private String path;
    @NotBlank(message = "文件名不为空")
    @Schema(description = "文件名", example = "xxx.jpg")
    private String name;
    @NotNull(message = "文件大小不为空")
    @Schema(description = "文件大小", example = "1024")
    private Integer size;
}
