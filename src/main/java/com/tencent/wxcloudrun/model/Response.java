package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    public int status;
    public String message;
    public Object data;

    public Response(){
        this.message = ReturnCode.SUCCESS.getMessage();
        this.status = ReturnCode.SUCCESS.getCode();
    }
    public Response(int status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public Response(Object data){
        this.data = data;
        this.message = ReturnCode.SUCCESS.getMessage();
        this.status = ReturnCode.SUCCESS.getCode();
    }
    public Response(ReturnCode rc){
        this.message = rc.getMessage();
        this.status = rc.getCode();
    }
    public Response(ReturnCode rc,Object data){
        this(rc);
        this.data = data;
    }
}
