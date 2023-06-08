package com.tencent.wxcloudrun.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
public class Rental {
    // 发布人用户ID
    @Schema(description = "发布人用户ID", example = "o1d2a3s4d5f6g7h8j9k0l1z2x3c4v5b6")
    private String userID;
    // 发布人头像
    @Schema(description = "发布人头像序号", example = "1")
    private String sellerAvatar;
    // 发布人昵称
    @Schema(description = "发布人昵称", example = "张三")
    private String sellerNickname;
    @Schema(description = "转租ID", example = "1")
    public Integer rentalID;
    //性别要求: 0 为男性，1为女性，2为随便
    @Schema(description = "性别要求: 0 为男性，1为女性，2为随便", example = "2")
    public Integer sexConstraint;
    // 户型
    @Schema(description = "户型", example = "2B1B")
    public String floorPlan;
    @Schema(description = "描述", example = "风水好，交通方便，价格实惠")
    public String description;
    // 发布时间
    @Schema(description = "发布时间", example = "2021-01-01 00:00:00")
    public Timestamp publishedTime;
    @Schema(description = "发布时间(UTC)", example = "2021-01-01 00:00:00")
    public String UTCPublishedTime;
    // 转租开始日期
    @Schema(description = "转租开始日期", example = "2021-01-01")
    public Timestamp rentalStartTime;
    // 转租结束日期
    @Schema(description = "转租结束日期", example = "2021-01-01")
    public Timestamp rentalEndTime;
    // 价格
    @Schema(description = "价格", example = "6000")
    public Integer price;
    @Schema(description = "地址", example = "上海市浦东新区")
    public String location;
    @Schema(description = "联系方式（微信号）", example = "sunboy41")
    public String contact;
    // 图片URL
    @Schema(description = "图片URL")
    private ArrayList<String> images;
    @Schema(hidden = true)
    private String imagesJSON;


}
