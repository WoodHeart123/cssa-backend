package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping({"/rental"})
public class RentalController {

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
        return null;
    }

    /**
     * 搜索转租信息
     * @param value 用户输入字符
     * @return 匹配的转租信
     */
    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, HttpServletRequest request){
        return null;
    }

    /**
     * 提供用户联想输入
     * @param value 用户输入字符
     * @return 字符串列表，包含10个联想词语
     */
    @RequestMapping(value={ "/suggest"}, method = {RequestMethod.GET})
    public Response suggest(@RequestParam String value, HttpServletRequest request){
        return null;
    }

    /**
     *记录用户输入的转租信息
     * @param rentalInfo 转租信息
     */
    @RequestMapping(value={"/postRentalInfo"}, method = {RequestMethod.POST})
    public Response postRentalInfo(@RequestBody Rental rentalInfo, HttpServletRequest request){
        return null;
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
