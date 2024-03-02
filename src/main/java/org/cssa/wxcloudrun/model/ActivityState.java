package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

public class ActivityState {

    // 活动ID
    @Schema(description = "活动ID", example = "1")
    @JSONField(name = "id")
    private Integer activity_id ;

    // 活动标题
    @Schema(description = "活动标题", example = "中秋节赏月")
    private String title;

    // 活动开始时间
    @Schema(description = "活动开始时间", example = "2020-12-31 23:59:59")
    private Timestamp start_time;

    // 活动结束时间
    @Schema(description = "活动结束时间", example = "2020-12-31 23:59:59")
    private Timestamp end_time;

    // 活动地点
    @Schema(description = "活动地点", example = "Union South")
    private String location;

    // 活动文宣
    @Schema(description = "活动文宣", example = "{XXX},{XXX}")
    private String announcement;

    @Schema(description = "活动报名费用", example = "$20")
    private Integer fee;

    @Schema(description="活动限制人数", example = "100")
    private Integer capacity_limit;

    @Schema(description = "活动描述", example = "XXXXX")
    private String description;

    @Schema(description = "活动报名截止日期", example = "2020-12-31 23:59:59")
    private Timestamp deadline;

    @Schema(description = "活动负责人+邮箱", example = "小明_xiaoming@wisc.edu")
    private String responsible_person;

    @Schema(description = "活动更新日志", example = "{XXX},{XXX}")
    private String update_log;

    @Schema(description = "上次更新时间", example = "2020-12-31 23:59:59")
    private Timestamp last_updated;

    @Schema(description = "活动状态删除时间", example = "2020-12-31 23:59:59")
    private String deleted_at;

    @Schema(description = "活动发布时间", example = "2020-12-31 23:59:59")
    private String published_at;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    private String additionalInfoJSON;
}
