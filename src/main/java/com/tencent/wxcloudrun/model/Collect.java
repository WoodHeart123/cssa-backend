package com.tencent.wxcloudrun.model;


import lombok.Data;
import java.sql.Timestamp;

@Data
public class Collect {
    private String collectID;
    private String userID;
    private Integer contentID;
    private CollectType collectType;
    private Timestamp createTime;
}
