package org.cssa.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Admin {
    private Integer adminID;
    private String username;
    @JSONField(serialize = false)
    private String password;
    private Division department;
    private Boolean validity;
    // 0=超级管理员；1=管理员；2=用户；
    private Integer adminType;
    private String token;

    public enum Division {
        JISHU,
        WAILIAN,
        HUODONG,
        DUOMEITI,
        XUANCHUAN,
    }
}
