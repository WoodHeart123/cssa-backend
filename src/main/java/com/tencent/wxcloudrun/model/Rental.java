package com.tencent.wxcloudrun.model;


import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Rental {
    // 发布人用户ID
    private String userID;
    // 发布人头像
    private String sellerAvatar;
    // 发布人昵称
    private String sellerNickname;
    public Integer rentalID;
    //性别要求: 0 为男性，1为女性，2为随便
    public Integer sexConstraint;
    // 户型
    public String floorPlan;
    public String description;
    // 发布时间
    public Timestamp publishedTime;
    public String UTCPublishedTime;
    // 转租开始日期
    public Timestamp rentalStartTime;
    // 转租结束日期
    public Timestamp rentalEndTime;
    // 价格
    public Integer price;
    public String location;
    public String contact;
    // 图片URL
    private ArrayList<String> images;
    private String imagesJSON;



}
