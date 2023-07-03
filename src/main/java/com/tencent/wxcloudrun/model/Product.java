package com.tencent.wxcloudrun.model;

import com.tencent.wxcloudrun.validation.DateTimeStr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Product {
    @Schema(description = "商品ID", example = "1")
    @NotNull(message = "商品ID不能为空")
    private Integer productID;
    @Schema(description = "发布人用户ID", example = "f3sd-21ad")
    @NotBlank(message = "发布人用户ID不能为空")
    private String userID;
    @Schema(description = "发布人头像序号", example = "1")
    @NotNull(message = "发布人头像序号不能为空")
    private Integer sellerAvatar;
    @Schema(description = "发布人昵称", example = "张三")
    @NotBlank(message = "发布人昵称不能为空")
    private String sellerNickname;
    @Schema(description = "商品标题", example = "iPhone 12")
    @NotBlank(message = "商品标题不能为空")
    private String productTitle;
    @Schema(description = "商品描述", example = "iPhone 12 128G 白色")
    @NotBlank(message = "商品描述不能为空")
    private String productDescription;
    @Schema(description = "商品价格", example = "6000")
    @NotNull(message = "商品价格不能为空")
    @Min(value = 1, message = "价格最少不低于1美金")
    @Max(value = 10000, message = "价格不能超过10000")
    private Integer price;
    @Schema(description = "商品类型")
    @NotEmpty(message = "商品类型不为空")
    private ProductType productType;
    @Schema(description = "商品成色")
    @NotEmpty(message = "商品成色不为空")
    private Condition productCondition;
    @Schema(description = "商品发布时间", example = "2021-01-01 00:00:00")
    @NotEmpty(message = "发布时间不为空")
    @DateTimeStr(message = "发布时间格式不对", format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time;
    @Schema(description = "商品发布时间(UTC)", example = "2021-01-01 00:00:00")
    @NotEmpty(message = "发布时间不为空")
    @DateTimeStr(message = "发布时间格式不对", format = "yyyy-MM-dd HH:mm:ss")
    private String UTCtime;
    @Schema(description = "联系方式（微信号）", example = "sunboy41")
    @NotBlank(message = "联系方式不为空")
    private String contact;
    @Schema(description = "商品图片URL")
    @Size(min = 1, max = 5, message = "图片只能上传1-5张")
    private List<String> images;
    @Schema(hidden = true)
    private String imagesJSON;
    @Schema(description = "商品交易方式", example = "自提")
    @NotBlank(message = "交易方式不为空")
    private String delivery;

}
