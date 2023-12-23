package com.tencent.wxcloudrun.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
public class FriendPost {

    // 活动ID
    @Schema(description = "帖子ID", example = "1")
    @JSONField(name = "id")
    private Integer id;

    @Schema(description = "用户ID", example = "f3sd-21ad")
    private String userID;

    // 活动标题
    @Schema(description = "标题", example = "中秋节赏月")
    private String title;

    // 图片URL
    @Schema(description = "图片URL", example = "[https://xxx.com/xxx.jpg]")
    private ArrayList<String> images;

    // 数据库存储JSON, 无需对外暴露
    @Schema(hidden = true)
    @JSONField(serialize = false, deserialize = false)
    private String imagesJSON;

    // 活动描述
    @Schema(description = "描述", example = "中秋节赏月")
    private String description;

    @Schema(description = "创建时间", example = "1")
    @JSONField(name = "created_at")
    private Timestamp createdAt;

    @Schema(description = "更新时间", example = "1")
    @JSONField(name = "updated_at")
    private Timestamp updatedAt;

    @Schema(description = "删除时间", example = "1")
    @JSONField(name = "deleted_at")
    private Timestamp deletedAt;
}
