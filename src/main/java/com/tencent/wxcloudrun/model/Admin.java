package com.tencent.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NonNull;

@Data
public class Admin {
    public enum Division{
        JISHU,
        WAILIAN,
        HUODONG,
        DUOMEITI,
        XUANCHUAN,
    }

    private Integer adminID;
    private String username;
    @JSONField(serialize = false)
    private String password;
    private Division department;
    private Boolean validity;
    // 0=超级管理员；1=管理员；2=用户；
    private Integer adminType;
    private String token;
}
