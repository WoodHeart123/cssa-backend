package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

/**
 * Promotion 类表示广告和活动的推广活动。
 * 它包含管理广告或活动生命周期所必需的属性，
 * 如其类型、标签、相关的 URL 地址和展示推广期限。
 */
@Data
public class Promotion {
    @Schema(description = "推广id", example = "1")
    @JSONField(name = "promotion_id")
    private Integer promotionId;

    @Schema(description = "推广类型；0：广告，1：活动", example = "1")
    private Integer type;

    @Schema(description = "每个推广的文字标签", example = "1")
    private String label;

    @Schema(description = "存储展示图片的URL地址")
    @JsonAlias({"imageUrl", "image_url"})
    private String imageUrl;

    @Schema(description = "存储点击广告后跳转的URL链接")
    @JsonAlias({"targetUrl", "target_url"})
    private String targetUrl;

    @Schema(description = "推广开始时间")
    @JsonAlias({"startDate", "start_date"})
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate startDate;

    @Schema(description = "推广结束时间")
    @JsonAlias({"endDate", "end_date"})
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate endDate;

    @Schema(description = "是否为默认展示图片；默认值为0为“否”，1为“是”")
    @JsonAlias({"isDefault", "is_default"})
    private Boolean isDefault;
}
