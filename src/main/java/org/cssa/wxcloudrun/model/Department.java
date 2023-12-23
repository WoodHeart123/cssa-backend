package org.cssa.wxcloudrun.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Department {
    @Schema(description = "院系ID", example = "1")
    private String department;
    @Schema(description = "院系简称", example = "CS")
    private String departmentAbrev;
    @Schema(description = "院系ID", example = "19")
    private Integer departmentID;
    @Schema(description = "评论数量", example = "203")
    private Integer commentNum;
}
