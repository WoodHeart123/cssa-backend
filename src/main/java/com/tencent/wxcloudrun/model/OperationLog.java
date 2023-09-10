package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Hidden
@Builder
public class OperationLog {
    private Integer id; // 主键
    private String userID; // 操作人
    private String resource; // 操作的资源
    private String requestMethod; // 请求方式
    private String beanName; // 操作的类
    private String requestParams; // 请求的参数
    private String responseData; // 返回数据
    private Timestamp createTime; // 创建时间
}
