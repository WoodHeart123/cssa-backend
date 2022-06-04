package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SignupInfo {
    private String userID;
    private Integer actID;
    private Double discount;
    private String response;
    private Boolean ifJoined;

}
