package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SignupInfo {
    @Schema(description = "用户ID", example = "oXv1-5Q0Z1QY1QY1QY1QY1QY1QY1")
    private String userID;
    @Schema(description = "活动ID", example = "1")
    private Integer actID;
    @Schema(description = "用户回答")
    private List<String> response;
    @Schema(hidden = true)
    private String responseJSON;
    @Schema(description = "是否已经参加", example = "true")
    private Boolean ifJoined;
    @Schema(description = "支付金额", example = "100")
    private Integer payment;
    @Schema(description = "用户昵称", example = "张三")
    private String nickname;
}
