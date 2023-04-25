package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface RestaurantService {
    Response getRestaurantList(Integer aboveRating, Integer priceLimit, Integer offset, Integer limit);
}
