package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Restaurant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    List<Restaurant> getRestaurantList(Integer priceLimit, Integer aboveRating, Integer offset, Integer limit);
}
