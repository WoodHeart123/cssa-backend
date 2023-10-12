package com.tencent.wxcloudrun.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Product {
    @Schema(description = "商品ID", example = "1")
    private Integer productID;
    @Schema(description = "发布人用户ID", example = "f3sd-21ad")
    private String userID;
    @Schema(description = "发布人头像序号", example = "1")
    private Integer sellerAvatar;
    @Schema(description = "发布人昵称", example = "张三")
    private String sellerNickname;
    @Schema(description = "商品标题", example = "iPhone 12")
    private String productTitle;
    @Schema(description = "商品描述", example = "iPhone 12 128G 白色")
    private String productDescription;
    @Schema(description = "商品价格", example = "6000")
    private Integer price;
    @Schema(description = "商品类型")
    private ProductType productType;
    @Schema(description = "商品成色")
    @JSONField
    private Condition productCondition;
    @Schema(description = "商品发布时间", example = "2021-01-01 00:00:00")
    private Timestamp time;
    @Schema(description = "商品发布时间(UTC)", example = "2021-01-01 00:00:00")
    @JSONField(name = "UTCtime")
    private String UTCtime;
    @Schema(description = "联系方式（微信号）", example = "sunboy41")
    private String contact;
    @Schema(description = "商品图片URL")
    private List<String> images;
    @Schema(hidden = true)
    private String imagesJSON;
    @Schema(description = "商品交易方式", example = "自提")
    private String delivery;

}
