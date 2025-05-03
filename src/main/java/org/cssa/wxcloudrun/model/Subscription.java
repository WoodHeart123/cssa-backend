package org.cssa.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class Subscription {
    @Schema(description = "用户ID", example = "oXv1-5Q0Z1QY1QY1QY1QY1QY1QY1")
    private String openID;

    @Schema(description = "订阅邮箱", example = "zSan@wisc.edu")
    private String email;

    public Subscription(String openID, String email) {
        this.openID = openID;
        this.email = email;
    }
}
