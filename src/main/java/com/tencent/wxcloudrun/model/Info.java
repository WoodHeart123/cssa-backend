package com.tencent.wxcloudrun.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Info {
    public enum typeEnum {
        INPUT, SELECT
    }
    @Schema(description = "选项类型", example = "INPUT")
    public typeEnum type;
    @Schema(description = "选项名称", example = "名字")
    public String name;
    @Schema(description = "选项列表(仅在选择使用)", example = "[\"张三\",\"李四\",\"王五\"]")
    public ArrayList<String> options;
    @Schema(description = "选项值", example = "张三")
    public String value;
}
