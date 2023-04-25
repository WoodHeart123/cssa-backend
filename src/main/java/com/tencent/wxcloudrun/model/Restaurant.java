package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class Restaurant {
    private Integer restaurantID;
    private String name;
    private String location;
    private Float avgRating;
    private Integer avgPrice;
    private Integer commentCount;
    private Float latitude;
    private Float longitude;
}
