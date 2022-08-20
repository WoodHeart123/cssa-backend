package com.tencent.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class User {
    
    private String userID;
    private String email;
}
