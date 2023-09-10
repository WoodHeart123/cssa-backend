package com.tencent.wxcloudrun.model;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

@Data
@Hidden
public class EmailDetail {
    private String receiver;
    private String message;
    private String subject;
    // not available
    //private String attachment;
}
