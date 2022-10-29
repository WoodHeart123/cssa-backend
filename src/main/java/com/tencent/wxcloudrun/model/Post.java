package com.tencent.wxcloudrun.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;

import java.sql.Timestamp;
import java.util.List;

/**
 * 发布商品
 */
@Data
public class Post {

    private String userID;
    private Integer productID;
    private String label;
    private Integer price;
    private String situation;
    private String getWay;
    private String contactID;
    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp releaseTime;

}
