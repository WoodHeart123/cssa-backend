package org.cssa.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Contact 类表示用户的联系信息，可以包括电话号码和微信 ID。
 */
@Data
public class Contact {

    // 电话号码 (可选)
    @Schema(description = "电话号码", example = "123-456-7890")
    private String phoneNumber;

    // 微信 ID (可选)
    @Schema(description = "微信 ID", example = "weixin123")
    private String weChatId;

    // 邮箱 （可选）
    @Schema(description = "邮箱", example = "abc@wisc.edu")
    private String email;
}
