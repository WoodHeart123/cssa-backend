package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Mate {
    // 活动ID
    @Schema(description = "活动ID", example = "1")
    private Integer actID;

    // 发布者ID
    @Schema(description = "发布者ID", example = "1")
    private String posterID;

    @Schema(description = "发布时间", example = "2020-12-31 23:59:59")
    private Timestamp postDate;

    // 活动地点
    @Schema(description = "活动地点", example = "腾讯大厦")
    private String location;

    // 活动标题
    @Schema(description = "活动标题", example = "中秋节赏月")
    private String title;

    // 性别限定
    @Schema(description = "性别限定", example = "0: 任意，1: 男，2: 女")
    private Integer sex;

    // 图片URL
    @Schema(description = "图片URL", example = "https://xxx.com/xxx.jpg")
    private String imgs;

    // 活动描述
    @Schema(description = "找搭子描述", example = "饭搭子")
    private String description;

    // 活动额外填写信息
    @Schema(description = "标签")
    private List<String> tags;

    // 活动额外填写信息
    @Schema(description = "额外填写信息")
    private List<Info> additionalInfo;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    private String additionalInfoJSON;
}
