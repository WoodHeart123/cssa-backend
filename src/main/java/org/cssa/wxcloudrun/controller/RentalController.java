package org.cssa.wxcloudrun.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.RentalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.service.WeChatAPI;
import org.cssa.wxcloudrun.util.InjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/rental"})
@Tag(name = "转租中心")
public class RentalController {

    @Autowired
    RentalService rentalService;

    @Autowired
    WeChatAPI weChatAPI;

    /**
     * 获取转租信息
     *
     * @param offset 从第几行返回
     * @param limit  返回多少个
     * @param query  filter参数
     *               "floorPlanList":["studio","1B1B"] 仅显示合适户型
     *               "priceLimit":[最高价]
     *               "time":[开始时间，结束时间]
     * @return 转租信息列表
     */
    @RequestMapping(value = {"/getRentalList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取转租信息", description = "获取转租信息")
    public Response<List<Rental>> getRentalList(@Parameter(description = "") @RequestParam Integer offset,
                                                @Parameter(description = "") @RequestParam Integer limit,
                                                @Parameter(description = "最高价格限制") @RequestParam Integer priceLimit,
                                                @Parameter(description = "接受的户型") @RequestParam ArrayList<String> floorPlanList,
                                                @Parameter(description = "开始时间，结束时间") @RequestParam ArrayList<Long> time) {
        return rentalService.getRentalList(offset, limit, priceLimit,
                floorPlanList, new Timestamp(time.get(0)), new Timestamp(time.get(1)));
    }

    @RequestMapping(value = {"/getRental"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取特定转租信息", description = "获取特定转租信息")
    public Response<Rental> getRental(@Parameter(description = "") @RequestParam Integer rentalID) {
        return rentalService.getRental(rentalID);
    }

    /**
     * 记录用户输入的转租信息
     *
     * @param rentalInfo 转租信息
     */
    @RequestMapping(value = {"/postRentalInfo"}, method = {RequestMethod.POST})
    @Operation(summary = "记录用户输入的转租信息", description = "记录用户输入的转租信息")
    public Response<Object> postRentalInfo(@Parameter(description = "转租信息") @RequestBody Rental rentalInfo,
                                           @Parameter(description = "是否保存联系方式") @RequestParam Boolean save,
                                           @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        WechatResponse wechatResponse = weChatAPI.MsgCheck(rentalInfo.getDescription() + ";" + rentalInfo.getLocation(), user.getOpenID(), 3);
        if(wechatResponse.getResult() != null && wechatResponse.getResult().getLabel() >= 20000){
            return new Response<>(ReturnCode.CENSORED_UGC_CONTENT);
        }
        rentalInfo.setUserID(user.getId());
        return rentalService.postRentalInfo(rentalInfo, save);
    }

    @RequestMapping(value = {"/updateRental"}, method = {RequestMethod.POST})
    @Operation(summary = "更新转租信息", description = "更新转租信息")
    public Response<Object> updateRental(@Parameter(description = "转租信息") @RequestBody Rental rental,
                                         @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        return rentalService.updateRental(user.getId(), rental);
    }

    @RequestMapping(value = {"/deleteRental"}, method = RequestMethod.DELETE)
    public Response<Object> deleteRental(@RequestParam Integer productID,
                                                 @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        return rentalService.deleteRental(user.getId(), productID);
    }

    @RequestMapping(value = {"/getUserRentalList"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Rental>> getUserRental(@RequestParam Integer offset,
                                                     @RequestParam Integer limit,
                                                     @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        return rentalService.getUserRental(user.getId(), offset, limit);
    };



}
