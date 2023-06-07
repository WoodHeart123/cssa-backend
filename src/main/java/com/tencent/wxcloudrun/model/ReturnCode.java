package com.tencent.wxcloudrun.model;


import lombok.Getter;

@Getter
public enum ReturnCode {
    SUCCESS(100, "请求成功"),
    ACTIVITY_NOT_EXIST(101, "活动不存在"),
    INVALID_USER_TOKEN(102, "用户信息无效"),
    EXCEED_LENGTH_LIMIT(103, "超过字数限制"),
    NO_DEPARTMENT_ID(104, "无专业信息"),
    LACK_PARAM(105, "缺少参数"),
    AUTH_FALSE(106, "验证码错误"),
    REPEATED_ZAN(107, "已点赞"),
    INTEGER_OUT_OF_RANGE(108, "整型超过范围"),
    EMPTY_STRING(109, "空字符串"),
    INVALID_ADMIN_TOKEN(301, "管理者信息无效"),
    INVALID_ENUM_TYPE(110, "无法识别枚举类型"),
    UNKNOWN_SERVICE(111, "无法识别服务项目"),
    INVALID_TYPE(112, "类型错误"),

    INVALID_ADMIN_INFO(301, "管理员登录信息错误");

    private final Integer code;
    private final String message;

    ReturnCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
