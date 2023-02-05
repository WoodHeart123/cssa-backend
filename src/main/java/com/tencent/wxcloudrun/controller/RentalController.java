package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.RentalService;
import com.tencent.wxcloudrun.service.SecondHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.SearchResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
     * @param offset 从第几行返回
     * @param limit  返回多少个
     * @param query  filter参数
     *         "floorPlan":["studio","1B1B"] 仅显示合适户型
     *         "price":[最高价]
     *         "time":[开始时间，结束时间]
     * @return 转租信息列表
     */
    @RequestMapping(value={ "/getRentalList"}, method = {RequestMethod.POST})
    public Response getRentalList(@RequestParam Integer offset, @RequestParam Integer limit, @RequestBody Map<String, ArrayList<String>> query, HttpServletRequest request){
        return rentalService.getRentalList(offset,limit, query);
    }

    /**
     * 搜索转租信息
     * @param value 用户输入字符
     * @return 匹配的转租信
     */
    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, HttpServletRequest request){
        ArrayList<String> rentalIDList = new ArrayList<>();
        Query q = new Query("*" + value + "*").setLanguage("chinese");//???English?
        SearchResult sr = jedisPooled.ftSearch("rental-index",q);
        for(Document document: sr.getDocuments()){
            rentalIDList.add(document.getString("rentalID"));
        }
        if(rentalIDList.size() == 0){
            return Response.builder().message("没有匹配结果").status(124).build();
        }
        return rentalService.getRental(rentalIDList);
    }

    /**
     * 提供用户联想输入
     * @param value 用户输入字符
     * @return 字符串列表，包含10个联想词语
     */
    @RequestMapping(value={ "/suggest"}, method = {RequestMethod.GET})
    public Response suggest(@RequestParam String value, HttpServletRequest request){
        return Response.builder().data(jedisPooled.ftSugGet("rentalLocation", value, true, 10)).status(100).build();
    }

    /**
     *记录用户输入的转租信息
     * @param rentalInfo 转租信息
     */
    @RequestMapping(value={"/postRentalInfo"}, method = {RequestMethod.POST})
    public Response postRentalInfo(@RequestBody Rental rentalInfo, HttpServletRequest request){
        return rentalService.postRentalInfo(rentalInfo);
    }

    /**
     * 收藏或取消收藏
     * @param rentalID 转租ID
     */
    @RequestMapping(value={"/saveRental"}, method = {RequestMethod.GET})
    public Response saveRentalInfo(@RequestParam Integer rentalID, @RequestParam Boolean save, HttpServletRequest request){
        return null;
    }


}
