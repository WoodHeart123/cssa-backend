package com.tencent.wxcloudrun.model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class ActivityResponse {
    // response ID
    @Schema(description = "response ID", example = "1")
    private Integer ID;

    // 活动ID
    @Schema(description = "活动ID", example = "1")
    private Integer activityID;

    // 用户ID
    @Schema(description = "用户ID", example = "1")
    private Integer userID;

    // 报名信息
    @Schema(description = "报名回复信息", example = "收到")
    private String response;

    // 是否付款
    @Schema(description = "是否付款", example = "1")
    private Integer isPaid;

    // 创建时间
    @Schema(description = "创建时间", example = "2020-12-31 23:59:59")
    private Timestamp createdAt;

    // 更新时间
    @Schema(description = "更新时间", example = "2020-12-31 23:59:59")
    private Timestamp updatedAt;

    // 删除时间
    @Schema(description = "删除时间", example = "2020-12-31 23:59:59")
    private Timestamp deletedAt;

}
