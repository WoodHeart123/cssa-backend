package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.RediSearchUtil;
import redis.clients.jedis.search.SearchResult;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping({"/rental"})
public class RentalController {

    @Autowired
    RentalService rentalService;

    @Autowired
    private JedisPooled jedisPooled;

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
    @RequestMapping(value = {"/getRentalList"}, method = {RequestMethod.GET})
    public Response getRentalList(@RequestParam Integer offset, @RequestParam Integer limit, @RequestParam Integer priceLimit, @RequestParam ArrayList<String> floorPlanList,@RequestParam ArrayList<Long> time) {
        return rentalService.getRentalList(offset, limit, priceLimit,
                floorPlanList, new Timestamp(time.get(0)), new Timestamp(time.get(1)));
    }

    /**
     * 记录用户输入的转租信息
     *
     * @param rentalInfo 转租信息
     */
    @RequestMapping(value = {"/postRentalInfo"}, method = {RequestMethod.POST})
    public Response postRentalInfo(@RequestBody Rental rentalInfo, @RequestParam Boolean save, @RequestHeader("x-wx-openid") String openid) {
        rentalInfo.setUserID(openid);
        return rentalService.postRentalInfo(rentalInfo,save);
    }


}
