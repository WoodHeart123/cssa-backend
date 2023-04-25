package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.RestaurantMapper;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.Restaurant;
import com.tencent.wxcloudrun.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantMapper restaurantMapper;
    @Override
    public Response getRestaurantList(Integer aboveRating, Integer priceLimit, Integer offset, Integer limit) {
        List<Restaurant> restaurantList = restaurantMapper.getRestaurantList(priceLimit, aboveRating, offset, limit);
        return Response.builder().data(restaurantList).message("").status(100).build();
    }
}
