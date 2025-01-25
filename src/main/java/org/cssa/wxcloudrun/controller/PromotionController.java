package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/promotion")
@Tag(name = "推广服务", description = "提供推广活动相关的API")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @RequestMapping(value = {"/createPromotion"}, method = {RequestMethod.POST})
    @Operation(summary = "创建一个推广", description = "创建一个推广")
    public Response<Boolean> createPromotion(@Parameter(description = "推广信息") @RequestBody Promotion promotion) {
        return promotionService.createPromotion(promotion);
    }

    @RequestMapping(value = {"/getOngoingPromotions"}, method = {RequestMethod.GET})
    @Operation(summary = "获取正在进行中的推广活动", description = "根据类型获取当前正在进行中的推广活动。如果类型为 null，则返回所有类型的活动。")
    public Response<List<Promotion>> getOngoingPromotions(@RequestParam(required = false) @Parameter(description = "推广活动的类型，如0为广告，1为活动。可为空。") Integer type) {
        return promotionService.getOngoingPromotions(type);
    }
}
