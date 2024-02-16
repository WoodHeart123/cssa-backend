package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
public class Activity {
    // 活动ID
    @Schema(description = "活动ID", example = "1")
    @JSONField(name = "id")
    private Integer id;

    // 活动标题
    @Schema(description = "活动标题", example = "中秋节赏月")
    private String title;

    // 活动描述
    @Schema(description = "活动描述", example = "中秋节赏月")
    private String description;

    @Schema(description = "活动开始时间", example = "2020-12-31 23:59:59")
    //@JSONField(name = "start_date")
    private Timestamp startDate;

    // 报名截止时间
    @Schema(description = "活动结束时间", example = "2020-12-31 23:59:59")
    //@JSONField(name = "end_date")
    private Timestamp endDate;

    // 报名截至时间
    @Schema(description = "报名截至时间", example = "2020-12-31 23:59:59")
    @JSONField(name = "deadline")
    private Timestamp deadline;

    // 活动地点
    @Schema(description = "活动地点", example = "腾讯大厦")
    private String location;

    // 人数报名上限
    @Schema(description = "人数报名上限", example = "100")
    private int capacity;

    // 图片URL
    @Schema(description = "图片URL", example = "https://xxx.com/xxx.jpg")
    private ArrayList<String> images;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    //@JSONField(serialize = false, deserialize = false)
    private String paymentJSON;

    @Schema(description = "付款信息")
    @JSONField(name = "payment")
    private PaymentOption payment;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    //@JSONField(serialize = false, deserialize = false)
    private String imagesJSON;

    // 活动价格
    @Schema(description = "价格", example = "100")
    private Integer price;

    @Schema(description = "活动参加人数", example = "1")
    private Integer userJoinedNum;


    @Schema(description = "活动创建时间", example = "1")
    @JSONField(name = "created_at")
    private Timestamp createdAt;

    @Schema(description = "活动更新时间", example = "1")
    @JSONField(name = "updated_at")
    private Timestamp updatedAt;

    @Schema(description = "活动删除时间", example = "1")
    @JSONField(name = "deleted_at")
    private Timestamp deletedAt;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    private String additionalInfoJSON;


}
