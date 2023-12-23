package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FriendComment {

    @Schema(description = "帖子ID", example = "1")
    @JSONField(name = "id")
    private Integer id;

    @Schema(description = "用户ID", example = "f3sd-21ad")
    @JSONField(name = "user_id")
    private String userID;

    @Schema(description = "帖子id", example = "1")
    @JSONField(name = "post_id")
    private Integer postID;

    @Schema(description = "评论内容", example = "中秋节赏月")
    private String content;

    @Schema(description = "创建时间", example = "1")
    @JSONField(name = "created_at")
    private Timestamp createdAt;

}
