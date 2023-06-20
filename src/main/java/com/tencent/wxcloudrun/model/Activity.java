package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Activity {
    // 活动ID
    @NotNull(message = "活动ID不能为空")
    @Schema(description = "活动ID", example = "1")
    private Integer actID;

    @NotBlank(message = "活动开始时间不能为空")
    @Schema(description = "活动开始时间", example = "2020-12-31 23:59:59")
    private Timestamp startDate;

    // 报名截止时间
    @NotBlank(message = "报名截止时间不能为空")
    @Schema(description = "报名截止时间", example = "2020-12-31 23:59:59")
    private Timestamp endDate;

    // 活动地点
    @NotBlank(message = "活动地点不能为空")
    @Schema(description = "活动地点", example = "腾讯大厦")
    private String location;

    // 人数报名上限
    @NotNull(message = "人数报名上限不能为空")
    @Min(value = 1, message = "人数上限不能小于1")
    @Schema(description = "人数报名上限", example = "100")
    private int capacity;

    // 活动参加人数
    @NotNull(message = "活动参加人数不能为空")
    @Min(value = 0, message = "活动参加人数不能小于0")
    @Schema(description = "活动参加人数", example = "7", defaultValue = "0")
    private int userJoinedNum;

    // 活动标题
    @NotBlank(message = "活动标题不能为空")
    @Schema(description = "活动标题", example = "中秋节赏月")
    private String title;

    // 图片URL
    @Schema(description = "图片URL", example = "https://xxx.com/xxx.jpg")
    private String imgs;

    // 活动价格
    @NotNull(message = "活动价格不能为空")
    @Min(value = 0, message = "价格不能小于0")
    @Schema(description = "价格", example = "100")
    private Integer price;

    // 活动描述
    @NotBlank(message = "活动描述不能为空")
    @Schema(description = "活动描述", example = "中秋节赏月")
    private String description;

    // 数据库存储JSON, 无需对外暴露
    @NotBlank(message = "数据库存储JSON不能为空")
    @Schema(hidden = true)
    private String additionalInfoJSON;

    // 活动额外填写信息
    @Schema(description = "活动额外填写信息")
    @NotEmpty(message = "活动内容不能为空")
    private List< @NotBlank(message = "活动内容不能为空") Info> additionalInfo;

}
