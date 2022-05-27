package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    public int status;
    public String message;
    public Object data;
}
