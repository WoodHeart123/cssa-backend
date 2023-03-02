package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Product {
    private Integer productID;
    private String userID;
    private Integer sellerAvatar;
    private String sellerNickname;
    private String productTitle;
    private String productDescription;
    private Integer price;
    private ProductType productType;
    private Condition productCondition;
    private Timestamp time;
    private String UTCtime;
    private String contact;
    private List<String> images;
    private String imagesJSON;
    private String delivery;
    
}
