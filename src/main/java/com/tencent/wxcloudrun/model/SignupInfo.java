package com.tencent.wxcloudrun.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class SignupInfo {
    @Schema(description = "报名ID", example = "1")
    private Integer id;

    @Schema(description = "用户ID", example = "oXv1-5Q0Z1QY1QY1QY1QY1QY1QY1")
    @JSONField(name = "user_id")
    private String userID;

    @Schema(description = "活动ID", example = "1")
    @JSONField(name = "act_id")
    private Integer actID;

    @Schema(description = "活动回复", example = "中秋节赏月")
    @JSONField(name = "response")
    private String responseJSON;

    @Schema(description = "是否已经参加", example = "true")
    private Boolean ifJoined;

    @Schema(description = "是否已经支付", example = "true")
    private Boolean isPaid;

    @Schema(description = "用户昵称", example = "张三")
    private String nickname;
}
