package com.tencent.wxcloudrun.model;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "返回结构体")
public class Response<T> {

    @Schema(description = "状态码", defaultValue = "100")
    public int status;
    @Schema(description = "返回消息", defaultValue = "")
    public String message;
    public T data;

    public Response(){
        this.message = ReturnCode.SUCCESS.getMessage();
        this.status = ReturnCode.SUCCESS.getCode();
    }
    public Response(int status,String message,T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public Response(T data){
        this.data = data;
        this.message = ReturnCode.SUCCESS.getMessage();
        this.status = ReturnCode.SUCCESS.getCode();
    }
    public Response(ReturnCode rc){
        this.message = rc.getMessage();
        this.status = rc.getCode();
    }
    public Response(ReturnCode rc,T data){
        this(rc);
        this.data = data;
    }
}
