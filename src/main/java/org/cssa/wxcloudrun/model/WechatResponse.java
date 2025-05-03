package org.cssa.wxcloudrun.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class WechatResponse {
    @Data
    public static class WechatResponseResult{

       String suggest;

       Integer label;
    }

    @JSONField(name="errcode")
    Integer errorCode;

    @JSONField(name="errmsg")
    String errorMessage;

    ArrayList<HashMap<String, String>> detail;

    WechatResponseResult result;
}
