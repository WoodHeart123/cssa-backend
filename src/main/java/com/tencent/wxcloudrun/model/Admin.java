package com.tencent.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NonNull;

@Data
public class Admin {
    private String username;
    @JSONField(serialize = false)
    private String password;
}
