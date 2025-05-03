package org.cssa.wxcloudrun.model;


import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class Message {
    public enum PostType{
        RENTAL,
        PRODUCT,
    }
    private Integer id;
    private String replierUserID;
    private String receiverUserID;
    private String content;
    private Integer postID;
    private PostType postType;

    @Schema(description = "留言时间", example = "1")
    @JSONField(name = "created_at")
    private Timestamp createdAt;

    @Schema(description = "留言删除时间", example = "1")
    @JSONField(name = "deleted_at")
    private Timestamp deletedAt;

    private User replier;
    private User receiver;
}
