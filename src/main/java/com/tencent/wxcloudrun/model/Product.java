package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Product {
    private Integer productID;
    private String userID;
    private String sellerAvatar;
    private String sellerNickname;
    private String productTitle;
    private String productDescription;
    private Integer price;
    private ProductType productType;
    private Condition condition;
    private Timestamp time;
    private String contact;
    private List<String> images;
    private String imagesJSON;
    private String delivery;
    
}
