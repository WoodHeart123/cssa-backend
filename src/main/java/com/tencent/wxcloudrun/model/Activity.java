package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Activity {
    // 活动ID
    public Integer actID;
    // 活动开始时间
    public Timestamp startDate;
    // 活动地点
    public String location;
    // 人数报名上限
    public int capacity;
    // 活动参加人数
    public int userJoinedNum;
    // 活动标题
    public String title;
    // 图片URL
    public String imgs;
    public Integer price;
    public String description;
    public String additionalInfoJSON;
    public List<info> additionalInfo;

}
