package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Ride 类表示顺风车服务或顺风车请求的信息。
 *
 * 该类用于存储发布的顺风车或请求顺风车的详细信息，包括用户的微信 openID、顺风车类型、价格、始发地、目的地等。
 * 根据 requestType 来区分是发布顺风车还是请求顺风车：
 * - requestType = 1 表示发布顺风车
 * - requestType = 2 表示请求顺风车
 *
 * 在顺风车发布的情况下，乘客人数代表空余座位数；在请求顺风车的情况下，乘客人数代表所需座位数。
 **/
@Data
public class Ride {

    // 微信用户的openID，用户唯一标识
    @Schema(description = "微信用户的openID", example = "oABC1234567890XYZ")
    private String userId;

    // 自动生成 rideID，系统自动生成
    @Schema(description = "顺风车 rideID，系统自动生成", example = "123")
    private Integer rideId;

    // 顺风车请求类型，1 表示出顺风车，2 表示请求顺风车，默认值为 1（出顺风车）
    @Schema(description = "顺风车请求类型，默认值为 1（出顺风车）；2（请求顺风车）", example = "1")
    private Integer requestType = 1;

    // 顺风车类型，1 表示单程，2 表示往返。与 requestType 独立，默认值为 1（单程）
    @Schema(description = "顺风车类型，1 表示单程，2 表示往返，默认值为 1（单程）", example = "1")
    private Integer rideType = 1;

    // 单程每人顺风车的价格；如顺风车请求类型为2，则为愿意支付的价格。
    @Schema(description = "单程每人顺风车的价格", example = "50")
    private Integer price;

    // 始发地
    @Schema(description = "始发地", example = "Madison")
    private String origin;

    // 目的地
    @Schema(description = "目的地", example = "Milwaukee")
    private String destination;

    // 出顺风车时可用座位数；如果是请求顺风车（2），则为 null
    @Schema(description = "出顺风车时可用座位数", example = "3")
    private Integer availableSeats;

    // 请求顺风车时所需座位数；如果是出顺风车（1），则为 null
    @Schema(description = "请求顺风车时所需座位数", example = "2")
    private Integer requestedSeats;

    // 出发时间
    @Schema(description = "出发时间", example = "2026-01-05 10:00:00")
    @JSONField(name = "departure_time")
    private Timestamp departureTime;

    // 返回时间 (选填)；如顺风车类型为2则为必填。
    @Schema(description = "返回时间 (选填)", example = "2026-01-05 18:00:00")
    @JSONField(name = "return_time")
    private Timestamp returnTime;

    // 顺风车描述，扩展为 500 字符
    @Schema(description = "顺风车描述", example = "2026.1.5 上午10点出发去芝加哥")
    private String description;

    // 联系信息
    @Schema(description = "联系信息，包括电话号码和微信 ID")
    private Contact contactInfo;

    // JSON字段，隐藏用于数据库存储联系信息
    @Schema(description = "数据库存储联系方式，不对外暴露",hidden = true)
    private String contactInfoJSON;

    // 汽车品牌 (选填)
    @Schema(description = "汽车品牌 (选填)", example = "Tesla")
    private String make;

    // 汽车类型 (选填)
    @Schema(description = "汽车类型 (选填)", example = "SUV")
    private String model;

    // 预计到达时间 (选填)
    @Schema(description = "预计到达时间 (选填)", example = "2026-01-05 12:30:00")
    @JSONField(name = "estimated_arrival_time")
    private Timestamp estimatedArrivalTime;

    // 被移除的时间 (可为 null)，如果不为 null，则表示顺风车信息未被移除
    @Schema(description = "顺风车信息被移除的时间 (如果不为 null，则表示未被移除)", example = "2026-01-10 15:00:00")
    @JSONField(name = "removed_time")
    private Timestamp removedTime;

    // 被发布的时间 (可为 null)，如果不为 null，则表示顺风车信息已发布
    @Schema(description = "顺风车信息被发布的时间 (如果不为 null，则表示已发布)", example = "2026-01-10 15:00:00")
    @JSONField(name = "published_time")
    private Timestamp publishedTime;

    // 图片列表，用户可以上传的图片信息
    @Schema(description = "顺风车相关的图片列表", example = "[\"https://server.com/image1.jpg\", \"https://server.com/image2.jpg\"]")
    private List<String> images;

    @Schema(description = "数据库存储图片，不对外暴露",hidden = true)
    private String imagesJSON;
}
