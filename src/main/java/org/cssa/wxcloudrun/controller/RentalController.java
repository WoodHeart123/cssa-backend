package org.cssa.wxcloudrun.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.cssa.wxcloudrun.model.Rental;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.RentalService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        if (offset <= 1) {
            offset = 0;
        }
        return rentalService.getRentalList(offset, limit, priceLimit,
                floorPlanList, new Timestamp(time.get(0)), new Timestamp(time.get(1)));
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
                                           @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        rentalInfo.setUserID(openid);
        return rentalService.postRentalInfo(rentalInfo, save);
    }

    @RequestMapping(value = {"/updateRental"}, method = {RequestMethod.POST})
    @Operation(summary = "更新转租信息", description = "更新转租信息")
    public Response<Object> updateRental(@Parameter(description = "转租信息") @RequestBody Rental rental,
                                         @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return rentalService.updateRental(openid, rental);
    }


}
