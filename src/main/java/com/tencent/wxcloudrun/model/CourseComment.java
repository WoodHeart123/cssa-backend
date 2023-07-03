package com.tencent.wxcloudrun.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tencent.wxcloudrun.validation.DateTimeStr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * 评论类
 */
@Data
public class CourseComment {

    @Schema(description = "用户ID", example = "adfd23fdfd")
    @NotBlank(message = "用户ID不能为空")
    private String userID;
    @Schema(description = "用户头像序号", example = "1")
    @NotNull(message = "用户头像序号不能为空")
    private Integer userAvatar;
    @Schema(description = "课程ID", example = "10")
    @NotNull(message = "课程ID不能为空")
    private Integer courseID;
    @Schema(description = "课程名称", example = "Computer Science 577")
    @NotBlank(message = "课程名称不能为空")
    private String courseName;
    @Schema(description = "课程号", example = "577")
    @NotNull(message = "课程号不为空")
    private Integer courseNum;
    @Schema(description = "院系简称", example = "CS")
    @NotBlank(message = "院系简称")
    private String departmentAbrev;
    @Schema(description = "评论ID", example = "19")
    private Integer commentID;
    //教授名
    @Schema(description = "教授名", example = "John Smith")
    @NotBlank(message = "教授民不能为空")
    private String professor;
    //上课时间 示例：2022Spring
    @Schema(description = "上课时间", example = "2022Spring")
    @NotBlank(message = "上课时间不能为空")
    private String courseTime;
    //难度 1-5
    @Schema(description = "难度", example = "3.5")
    @NotNull(message = "难度不能为空")
    private Double difficulty;
    //喜爱值 1-5
    @Schema(description = "推荐值", example = "4.5")
    @NotNull(message = "推荐不能为空")
    private Double prefer;
    @Schema(description = "评论内容", example = "This is a comment.")
    @NotBlank(message = "评论内容不能为空")
    private String comment;

    @Schema(description = "赞数量", example = "100")
    @NotNull(message = "点赞不能为空")
    private Integer likeCount;
    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "评论时间", example = "2020-12-31 23:59:59")
    @NotEmpty(message = "评论时间不为空")
    @DateTimeStr(message = "评论时间格式不对", format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp commentTime;

}
