package org.cssa.wxcloudrun.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PaymentOption {

    @Schema(description = "微信账号", example = "1233")
    private String wechat;
    @Schema(description = "zelle账号", example = "1233")
    private String zelle;
}
