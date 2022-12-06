package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MainPagePhoto {
    // 图片ID
    private Integer photoID;
    // 展示开始时间
    private Timestamp startDate;

    // 展示结束时间
    private Timestamp endDate;

    // 图片URL
    private String smallPhoto;
    private String largePhoto;
}
