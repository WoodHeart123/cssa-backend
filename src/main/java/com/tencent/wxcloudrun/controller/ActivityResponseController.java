package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.ActivityResponse;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.ReturnCode;
import com.tencent.wxcloudrun.service.ActivityResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/activityResponse")
@Api(tags = "活动报名")
public class ActivityResponseController {

    @Autowired
    private ActivityResponseService activityResponseService;

    @Operation(summary = "创建报名", description = "创建用户报名表单")
    @PostMapping("/createResponse")
    public Response<ReturnCode> createResponse(@ApiParam(value = "报名信息", required = true)
                                                   @RequestBody ActivityResponse activityResponse) {
        return activityResponseService.createResponse(activityResponse);
    }


    @Operation(summary = "获取报名内容", description = "获取用户报名信息详情")
    @GetMapping("/selectResponseContent/activity/{activityID}/user/{userID}")
    public Response<ActivityResponse> selectResponseContent(@ApiParam(value = "用户ID", required = true)
                                                                @PathVariable("userID") String userID,
                                                            @ApiParam(value = "活动ID", required = true)
                                                             @PathVariable("activityID") Integer activityID) {
        return activityResponseService.selectResponseContent(userID, activityID);
    }
    @Operation(summary = "删除报名内容", description = "删除用户报名信息")
    @DeleteMapping("/deleteResponse/{id}")
    public Response<ReturnCode> deleteResponse(@ApiParam(value = "报名信息ID", required = true)
                                                   @PathVariable("id") Integer id) {
        return activityResponseService.deleteResponse(id);
    }

}
