package org.cssa.wxcloudrun.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Event {

    @Schema(description = "活动ID", example = "1")
    private Integer id;
    @Schema(description = "活动标题", example = "中秋节赏月")
    private String title;
    @Schema(description = "活动封面图片", example = "https://xxx.com/xxx.jpg")
    private String image;
    @Schema(description = "活动开始时间", example = "2020-12-31 23:59:59")
    private Date time;
    @Schema(description = "活动描述", example = "中秋节赏月")
    private String description;
    @Schema(description = "活动地点", example = "腾讯大厦")
    private String locationName;
    @Schema(description = "活动地点经度", example = "1.0")
    private Double latitude;
    @Schema(description = "活动地点纬度", example = "1.0")
    private Double longitude;
    @Schema(description = "活动价格", example = "100")
    private Integer price;
    @Schema(description = "活动主持人ID", example = "100")
    private Integer hostUserId;
    @Schema(description = "活动创建时间", example = "1")
    private Timestamp createdAt;


}
