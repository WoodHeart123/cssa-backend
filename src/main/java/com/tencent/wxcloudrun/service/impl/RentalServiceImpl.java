package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.RentalMapper;
import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.RediSearchUtil;

import java.sql.Timestamp;
import java.util.*;


@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalMapper rentalMapper;
    @Autowired
    JedisPooled jedisPooled;



    @Override
    public Response getRental(ArrayList<String> rentalID) {
        return Response.builder().data(rentalMapper.getRentalByID(rentalID)).status(100).build();
    }

    @Override
    public Response getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime) {
        ArrayList<Rental> rentalArrayList;
        if(!startTime.equals(new Timestamp(0))){
            rentalArrayList = rentalMapper.getRentalTimed(offset,limit,priceLimit, floorplanList, startTime, endTime);
            // floorPlan
        }else{
            rentalArrayList = rentalMapper.getRental(offset,limit,priceLimit, floorplanList);
            for(Rental rental : rentalArrayList){
                rental.setImages((ArrayList<String>) JSON.parseArray(rental.getImagesJSON(),String.class));
            }
        }
        return Response.builder().data(rentalArrayList).status(100).build();
    }
    @Override
    @Transactional
    public Response postRentalInfo(Rental rentalInfo, Boolean save){
        rentalInfo.setPublishedTime(new Timestamp(new Date().getTime()));
        rentalInfo.setImagesJSON(JSON.toJSONString(rentalInfo.getImages()));
        rentalMapper.postRentalInfo(rentalInfo);
        if(save){
           rentalMapper.saveContact(rentalInfo.getUserID(), rentalInfo.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }

}
