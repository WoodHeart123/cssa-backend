package com.tencent.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Admin {
    public enum Division {
        JISHU,
        WAILIAN,
        HUODONG,
        DUOMEITI,
        XUANCHUAN,
    }

    @NotNull(message = "管理员ID不能为空")
    private Integer adminID;

    @NotBlank(message = "用户名不能为空")
    private String username;
    @JSONField(serialize = false)
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "院系不能为空")
    private Division department;
    @NotNull(message = "有效不能为空")
    private Boolean validity;


    // 0=超级管理员；1=管理员；2=用户；
    @NotNull(message = "管理权限不能为空")
    private Integer adminType;

    @NotBlank(message = "token不能为空")
    private String token;
}
