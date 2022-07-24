package com.tencent.wxcloudrun.model;


import lombok.Data;

@Data
public class EmailDetail {
    private String receiver;
    private String message;
    private String subject;
    // not available
    //private String attachment;
}
