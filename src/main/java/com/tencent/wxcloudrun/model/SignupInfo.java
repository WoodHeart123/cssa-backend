package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignupInfo {
    private String userID;
    private Integer actID;
    private Double discount;
    private List<String> response;
    private String responseJSON;
    private Boolean ifJoined;
    private Integer payment;
    private String nickname;
}
