package com.tencent.wxcloudrun.model;

import com.tencent.wxcloudrun.validation.DateTimeStr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class MainPagePhoto {
    // 图片ID
    @Schema(description = "图片ID", example = "1")
    @NotNull(message = "图片ID不能为空")
    private Integer photoID;
    // 展示开始时间
    @Schema(description = "展示开始时间", example = "2021-01-01 00:00:00")
    @NotEmpty(message = "开始时间不能为空")
    @DateTimeStr(message = "开始时间格式不对")
    private Timestamp startDate;

    // 展示结束时间
    @Schema(description = "展示结束时间", example = "2021-01-01 00:00:00")
    @NotEmpty(message = "结束时间不能为空")
    @DateTimeStr(message = "结束时间格式不对")
    private Timestamp endDate;
    // 图片URL
    @Schema(description = "缩略图片URL", example = "https://cloud.tencent.com/act/professor/1.jpg")
    private String smallPhoto;

    @Schema(description = "大图图片URL", example = "https://cloud.tencent.com/act/professor/1.jpg")
    private String largePhoto;
}
