package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.RestaurantComment;
import com.tencent.wxcloudrun.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping({"/restaurant"})
public class RestaurantController {


    @Autowired
    RestaurantService restaurantService;
    /**
     * 获取商家列表
      * @param aboveRating 只返回大于rating的数据
     * @param priceLimit 返回低于该价格的rating
     * @param offset 偏移量
     * @param limit 限制返回数量
     */
    @RequestMapping(value = {"/getRestaurantList"}, method = {RequestMethod.GET})
    public Response getRestaurantList(@RequestParam Optional<Integer> aboveRating,@RequestParam Optional<Integer> priceLimit, @RequestParam Integer offset, @RequestParam Integer limit){
        return restaurantService.getRestaurantList(aboveRating.orElse(0), priceLimit.orElse(10000),offset, limit);
    }

    /**
     * 获取商家的评论列表
     * @param restaurantID 商家ID
     * @param offset 偏移量
     * @param limit 限制返回数量
     */
    @RequestMapping(value = {"/getRestaurantComment"}, method = {RequestMethod.GET})
    public Response getRestaurantComment(@RequestParam Integer restaurantID, @RequestParam Integer offset, @RequestParam Integer limit){
        return null;
    }

    @RequestMapping(value = {"/postComment"}, method = {RequestMethod.POST})
    public Response postComment(@RequestBody RestaurantComment restaurantComment, @RequestHeader("x-wx-openid") String openid){
        restaurantComment.setUserID(openid);
        return null;
    }
}
