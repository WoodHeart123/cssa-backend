package com.tencent.wxcloudrun.model;


import com.tencent.wxcloudrun.validation.DateTimeStr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.ArrayList;

@Data
public class Rental {
    // 发布人用户ID
    @Schema(description = "发布人用户ID", example = "o1d2a3s4d5f6g7h8j9k0l1z2x3c4v5b6")
    @NotBlank(message = "userID不为空")
    private String userID;
    // 发布人头像
    @Schema(description = "发布人头像序号", example = "1")
    @NotBlank(message = "发布人头像不为空")
    private String sellerAvatar;
    // 发布人昵称
    @Schema(description = "发布人昵称", example = "张三")
    @NotBlank(message = "发布人昵称不为空")
    private String sellerNickname;
    @Schema(description = "转租ID", example = "1")
    @NotNull(message = "转租ID不为空")
    public Integer rentalID;
    //性别要求: 0 为男性，1为女性，2为随便
    @Schema(description = "性别要求: 0 为男性，1为女性，2为随便", example = "2")
    @NotNull(message = "性别不为空")
    @Max(value = 2)
    public Integer sexConstraint;
    // 户型
    @Schema(description = "户型", example = "2B1B")
    @NotBlank(message = "户型不为空")
    public String floorPlan;
    @Schema(description = "描述", example = "风水好，交通方便，价格实惠")
    @NotBlank(message = "描述不为空")
    public String description;
    // 发布时间
    @Schema(description = "发布时间", example = "2021-01-01 00:00:00")
    @NotEmpty(message = "发布时间不为空")
    @DateTimeStr(message = "发布时间格式不对", format = "yyyy-MM-dd HH:mm:ss")
    public Timestamp publishedTime;
    @Schema(description = "发布时间(UTC)", example = "2021-01-01 00:00:00")
    public String UTCPublishedTime;
    // 转租开始日期
    @Schema(description = "转租开始日期", example = "2021-01-01")
    @NotEmpty(message = "开始时间不为空")
    @DateTimeStr(message = "开始时间格式不对", format = "yyyy-MM-dd HH:mm:ss")
    public Timestamp rentalStartTime;
    // 转租结束日期
    @Schema(description = "转租结束日期", example = "2021-01-01")
    @NotEmpty(message = "结束时间不为空")
    @DateTimeStr(message = "结束时间格式不对", format = "yyyy-MM-dd HH:mm:ss")
    public Timestamp rentalEndTime;
    // 价格
    @Schema(description = "价格", example = "6000")
    @Min(value = 1, message = "价格不能低于$1")
    @Max(value = 20000, message = "价格不能高于$20000")
    public Integer price;
    @Schema(description = "地址", example = "上海市浦东新区")
    @NotBlank(message = "地址不为空")
    public String location;
    @Schema(description = "联系方式（微信号）", example = "sunboy41")
    @NotBlank(message = "微信号不为空")
    public String contact;
    // 图片URL
    @Schema(description = "图片URL")
    @Size(min = 1, max = 5, message = "上传的照片要在1-5之间")
    private ArrayList<String> images;
    @Schema(hidden = true)
    private String imagesJSON;


}
