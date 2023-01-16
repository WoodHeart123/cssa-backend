package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.RentalMapper;
import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalMapper rentalMapper;

    @Override
    public Response saveRentalInfo(Integer rentalID, boolean save, String userID) {
        User user = rentalMapper.collect(userID);
        List<Integer> rentalArrayList = JSON.parseArray(user.getSavedRentalJSON(), Integer.class);
        if(save){
          int i = 0;
          if(!(rentalArrayList.contains(rentalID))){
              i = 1;
              rentalArrayList.add(rentalID);
              String json = JSON.toJSONString(rentalArrayList);
              user.setSavedRentalJSON(json);
              RentalMapper.updateCollect(user);
              if (i == 1) {
                  return Response.builder().data(null).status(101).build();
              } else {
                  return Response.builder().data(null).status(100).build();
              }
          }
        }
        if(rentalArrayList.contains(rentalID)){
           rentalArrayList.remove(rentalID);
        }
        
    }
    @Override
    public Response getRentalList(Integer offset, Integer limit, Map<String, ArrayList<String>> query) {
        ArrayList<Rental> rentalArrayList;
        if(query == query.floorPlan){
            rentalArrayList = rentalMapper.getRentalList(offset,limit，query.floorPlan);
        }
        if(query == query.price){
            rentalArrayList = rentalMapper.getRentalList(offset,limit，query.price);
        }
        if(query == query.time){
            rentalArrayList = rentalMapper.getRentalList(offset,limit, query.time);
        }
        for(Rental rental: rentalArrayList){
            rental.setImages(JSON.parseArray(rental.getImagesJSON(),String.class));
        }
        return Response.builder().data(rentalArrayList).status(100).build();
    }
    @Override
    public Response postRentalInfo(Rental rentalInfo){
        rentalInfo.setTime(new Timestamp(new Date().getTime()));
        rentalInfo.setImagesJSON(JSON.toJSONString(rentalInfo.getImages()));
        rentalMapper.postRentalInfo(rentalInfo);
        return Response.builder().message("成功").status(100).build();
    }
     
}
