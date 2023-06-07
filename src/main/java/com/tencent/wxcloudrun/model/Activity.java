package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Activity {
    // 活动ID
    @Schema(description = "活动ID", example = "1")
    private Integer actID;

    @Schema(description = "活动开始时间", example = "2020-12-31 23:59:59")
    private Timestamp startDate;

    // 报名截止时间
    @Schema(description = "报名截止时间", example = "2020-12-31 23:59:59")
    private Timestamp endDate;

    // 活动地点
    @Schema(description = "活动地点", example = "腾讯大厦")
    private String location;

    // 人数报名上限
    @Schema(description = "人数报名上限", example = "100")
    private int capacity;

    // 活动参加人数
    @Schema(description = "活动参加人数", example = "7", defaultValue = "0")
    private int userJoinedNum;

    // 活动标题
    @Schema(description = "活动标题", example = "中秋节赏月")
    private String title;

    // 图片URL
    @Schema(description = "图片URL", example = "https://xxx.com/xxx.jpg")
    private String imgs;

    // 活动价格
    @Schema(description = "价格", example = "100")
    private Integer price;

    // 活动描述
    @Schema(description = "活动描述", example = "中秋节赏月")
    private String description;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    private String additionalInfoJSON;

    // 活动额外填写信息
    @Schema(description = "活动额外填写信息")
    private List<Info> additionalInfo;

}
