package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MainPagePhoto {
    // 图片ID
    @Schema(description = "图片ID", example = "1")
    private Integer photoID;
    // 展示开始时间
    @Schema(description = "展示开始时间", example = "2021-01-01 00:00:00")
    private Timestamp startDate;

    // 展示结束时间
    @Schema(description = "展示结束时间", example = "2021-01-01 00:00:00")
    private Timestamp endDate;
    // 图片URL
    @Schema(description = "缩略图片URL", example = "https://cloud.tencent.com/act/professor/1.jpg")
    private String smallPhoto;

    @Schema(description = "大图图片URL", example = "https://cloud.tencent.com/act/professor/1.jpg")
    private String largePhoto;
}
