package org.cssa.wxcloudrun.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 评论类
 */
@Data
public class CourseComment {

    @Schema(description = "用户ID", example = "adfd23fdfd")
    private String userID;
    @Schema(description = "用户头像序号", example = "1")
    private Integer userAvatar;
    @Schema(description = "课程ID", example = "10")
    private Integer courseID;
    @Schema(description = "课程名称", example = "Computer Science 577")
    private String courseName;
    @Schema(description = "课程号", example = "577")
    private Integer courseNum;
    @Schema(description = "院系简称", example = "CS")
    private String departmentAbrev;
    @Schema(description = "评论ID", example = "19")
    private Integer commentID;
    //教授名
    @Schema(description = "教授名", example = "John Smith")
    private String professor;
    //上课时间 示例：2022Spring
    @Schema(description = "上课时间", example = "2022Spring")
    private String courseTime;
    //难度 1-5
    @Schema(description = "难度", example = "3.5")
    private Double difficulty;
    //喜爱值 1-5
    @Schema(description = "推荐值", example = "4.5")
    private Double prefer;
    @Schema(description = "评论内容", example = "This is a comment.")
    private String comment;

    @Schema(description = "赞数量", example = "100")
    private Integer likeCount;
    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "评论时间", example = "2020-12-31 23:59:59")
    private Timestamp commentTime;

}
