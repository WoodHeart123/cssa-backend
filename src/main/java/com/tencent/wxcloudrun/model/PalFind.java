package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;

//id, user_id, label, title,
// content, images, created_at,
// updated_at, deleted_at
@Data
public class PalFind {
  @Schema(description = "发布人用户ID",example = "o1d2a3s4d5f6g7h8j9k0l1z2x3c4v5b6")
  private String UserID;

  @Schema(description = "发布人头像序号", example = "1")
  private String userAvatar;

  @Schema(description = "发布人昵称", example = "张三")
  private String userNickname;

  @Schema(description = "帖子ID", example = "1")
  public Integer postID;

  @Schema(description = "标签", example = "全部 课友 吃饭 运动 旅游 搭车 活动 相亲")
  public String title;

  @Schema(description = "标题", example = "8月14 ORD")
  public String label;

  @Schema(description = "描述", example = "求搭车")
  public String content;

  @Schema(description = "需求开始时间", example = "8-14 10:00")
  public Timestamp eventStartTime;

  @Schema(description = "需求结束时间", example = "8-14 14:00")
  public Timestamp eventEndTime;

  @Schema(description = "发布时间", example = "2021-01-01 00:00:00")
  public Timestamp createdAt;

//  @Schema(description = "发布时间(UTC)", example = "2021-01-01 00:00:00")
//  public String UTCPublishedTime;

  @Schema(description = "更新时间", example = "2021-01-01")
  public Timestamp updatedAt;

  @Schema(description = "删除时间", example = "2021-01-01")
  public Timestamp deletedAt;

//  @Schema(description = "联系方式（微信号）", example = "sunboy41")
//  public String contact;

  @Schema(description = "图片URL")
  private ArrayList<String> images;

  @Schema(hidden = true)
  private String imagesJSON;
}
