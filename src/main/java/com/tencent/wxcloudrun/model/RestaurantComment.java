package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RestaurantComment {
    private Integer commentID;
    private String comment;
    private String userID;
    private Integer rating;
    private Integer price;
    private ArrayList<String> images;
    private String imagesJSON;
}
