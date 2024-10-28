package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Ride {

    @Schema(description = "微信用户的openID", example = "oABC1234567890XYZ")
    private String userId;

    @Schema(description = "顺风车 rideID，系统自动生成", example = "123")
    private Integer rideId;

    @Schema(description = "顺风车请求类型，默认值为 1（出顺风车）；2（请求顺风车）", example = "1")
    private Integer requestType = 1;

    @Schema(description = "顺风车类型，1 表示单程，2 表示往返，默认值为 1（单程）", example = "1")
    private Integer rideType = 1;

    @Schema(description = "单程每人顺风车的价格", example = "50")
    private Integer price;

    @Schema(description = "始发地", example = "Madison")
    private String origin;

    @Schema(description = "目的地", example = "Milwaukee")
    private String destination;

    @Schema(description = "出顺风车时可用座位数", example = "3")
    private Integer availableSeats;

    @Schema(description = "请求顺风车时所需座位数", example = "2")
    private Integer requestedSeats;

    @Schema(description = "出发时间", example = "2026-01-05 10:00:00")
    @JSONField(name = "departure_time", format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp departureTime;

    @Schema(description = "返回时间 (选填)", example = "2026-01-05 18:00:00")
    @JSONField(name = "returnTime", format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp returnTime;

    @Schema(description = "顺风车描述", example = "2026.1.5 上午10点出发去芝加哥")
    private String description;

    @Schema(description = "联系信息，包括电话号码和微信 ID")
    private Contact contactInfo;

    @Schema(description = "数据库存储联系方式，不对外暴露", hidden = true)
    private String contactInfoJSON;

    @Schema(description = "汽车品牌 (选填)", example = "Tesla")
    private String make;

    @Schema(description = "汽车类型 (选填)", example = "SUV")
    private String model;

    @Schema(description = "预计到达时间 (选填)", example = "2026-01-05 12:30:00")
    @JSONField(name = "estimated_arrival_time", format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp estimatedArrivalTime;

    @Schema(description = "顺风车信息被移除的时间 (如果不为 null，则表示未被移除)", example = "2026-01-10T15:00:00")
    @JSONField(name = "removed_time")
    private Timestamp removedTime;

    @Schema(description = "顺风车信息被发布的时间 (如果不为 null，则表示已发布)", example = "2026-01-10T15:00:00")
    @JSONField(name = "published_time")
    private Timestamp publishedTime;

    @Schema(description = "顺风车相关的图片列表", example = "[\"https://server.com/image1.jpg\", \"https://server.com/image2.jpg\"]")
    private List<String> images;

    @Schema(description = "数据库内部存储图片，不对外暴露", hidden = true)
    private String imagesJSON;
}
