package org.cssa.wxcloudrun.model;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class User {

    private Integer id;
    @JSONField(serialize = false, deserialize = false)
    private String openID;
    private String email;
    private String nickname; // 用户的真实昵称
    private String phoneNumber;
    private Boolean isStudent;
    private Integer avatar;
    private String wechatID;
    private Boolean subscribe; // 是否订阅邮件通知
    private Boolean isBlocked; // 是否被拉黑
    private String avatarURL; // 用户的真实头像url

}
