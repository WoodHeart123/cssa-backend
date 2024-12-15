package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.RideService;
import org.cssa.wxcloudrun.service.UserService;
import org.cssa.wxcloudrun.service.WeChatAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/ride"})
@Tag(name = "顺风车服务")
public class RideController {

    @Autowired
    RideService rideService;

    @Autowired
    UserService userService;

    @Autowired
    WeChatAPI weChatAPI;

    // 获取未被移除的顺风车列表
    @RequestMapping(value = {"/getRideList"}, method = {RequestMethod.GET})
    @Operation(summary = "获取顺风车列表", description = "获取顺风车列表")
    public Response<List<Ride>> getRideList(@RequestParam Integer offset,
                                            @RequestParam Integer limit) {
        return rideService.getRideList(offset, limit, null);
    }

    // 获取指定用户已发布但未被移除的顺风车列表。
    @RequestMapping(value = {"/getRideListByUserId"}, method = {RequestMethod.GET})
    @Operation(summary = "根据用户微信openId获取顺风车列表", description = "获取顺风车列表")
    public Response<List<Ride>> getRideListByUserId(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openId,
                                                    @RequestParam Integer offset,
                                                    @RequestParam Integer limit) {
        return rideService.getRideList(offset, limit, openId);
    }

    // 获取该用户被移除的顺风车列表
    @RequestMapping(value = {"/getRemovedRideList"}, method = {RequestMethod.GET})
    @Operation(summary = "获取用户被移除的顺风车列表", description = "获取用户被移除的顺风车列表")
    public Response<List<Ride>> getRemovedRideList(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openId,
                                                   @RequestParam Integer offset,
                                                   @RequestParam Integer limit) {
        if (openId == null || StringUtils.isEmptyOrWhitespace(openId)) {
            return new Response<>(ReturnCode.INVALID_USER_TOKEN);
        }
        return rideService.getRemovedRideList(openId,offset, limit);
    }

    // 获取特定顺风车信息
    @RequestMapping(value = {"/getRide"}, method = {RequestMethod.GET})
    @Operation(summary = "获取特定顺风车信息", description = "获取特定顺风车信息")
    public Response<Ride> getRide(@RequestParam Integer rideId) {
        return rideService.getRide(rideId);
    }

    // 发布顺风车
    @RequestMapping(value = {"/publishRide"}, method = {RequestMethod.POST})
    @Operation(summary = "发布顺风车", description = "发布顺风车")
    public Response<Object> publishRide(@Parameter(description = "是否保存联系方式") @RequestParam(value = "save", required = false, defaultValue = "false") Boolean ifSave,
                                        @Parameter(description = "顺风车信息") @RequestBody Ride ride,
                                        @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openId) {
        // 内容检查
        String censoredContent = ride.getOrigin() + ";" + ride.getDestination() + ";" + ride.getDescription();
        WechatResponse wechatResponse = weChatAPI.MsgCheck(censoredContent, openId, 3);
        if(wechatResponse.getResult().getLabel() >= 20000){
            return new Response<>(ReturnCode.CENSORED_UGC_CONTENT);
        }

        // 设置用户 ID
        ride.setOpenId(openId);

        // 保存联系信息到用户
//        if (ifSave) {
//            Contact contactInfo = new Contact();
//            userService.saveContact(userId,contactInfo);
//        }

        // 保存顺风车信息
        if (rideService.publishRide(ride)) {
            return new Response<>(ReturnCode.SUCCESS);
        } else {
            return new Response<>(ReturnCode.ACTION_FAILED);
        }
    }

    // 更新顺风车信息
    @RequestMapping(value = {"/updateRide"}, method = {RequestMethod.POST})
    @Operation(summary = "更新顺风车信息", description = "更新顺风车信息")
    public Response<Object> updateRide(
            @Parameter(description = "顺风车信息") @RequestBody Ride ride,
            @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openId,
            @Parameter(description = "是否发布 (默认值为 false)")
            @RequestParam(value = "ifToPublish", required = false, defaultValue = "false") Boolean ifToPublish) {

        // 内容检查
        String censoredContent = ride.getOrigin() + ";" + ride.getDestination() + ";" + ride.getDescription();
        WechatResponse wechatResponse = weChatAPI.MsgCheck(censoredContent, openId, 3);
        if(wechatResponse.getResult().getLabel() >= 20000){
            return new Response<>(ReturnCode.CENSORED_UGC_CONTENT);
        }

        // 更新顺风车
        if (rideService.updateRide(openId, ride, ifToPublish)) {
            return new Response<>(ReturnCode.SUCCESS);
        } else {
            return new Response<>(ReturnCode.ACTION_FAILED);
        }
    }

    // 软删除顺风车 (移除顺风车)
    @RequestMapping(value = {"/removeRide"}, method = {RequestMethod.POST})
    @Operation(summary = "移除顺风车", description = "将顺风车从公共列表中移除，但保留在数据库中，用户仍然可以看到和编辑")
    public Response<Object> removeRide(@Parameter(description = "顺风车ID") @RequestParam Integer rideId,
                                       @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        // 检查该用户是否拥有该顺风车信息
        if (!rideService.isRideOwnedByUser(openid,rideId)) {
            return new Response<>(ReturnCode.RIDE_NOT_EXIST);
        }

        // 执行软删除操作
        if (rideService.removeRide(rideId)) {
            return new Response<>(ReturnCode.SUCCESS);
        } else {
            return new Response<>(ReturnCode.ACTION_FAILED);
        }
    }
}
