package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Activity {
    // 活动ID
    private Integer actID;
    // 活动开始时间
    private Timestamp startDate;
    // 活动地点
    private String location;
    // 人数报名上限
    private int capacity;
    // 活动参加人数
    private int userJoinedNum;
    // 活动标题
    private String title;
    // 图片URL
    private String imgs;
    private Integer price;
    private String description;
    private String additionalInfoJSON;
    private List<Info> additionalInfo;

}
