package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.RentalMapper;
import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalMapper rentalMapper;


    @Override
    public Response getRental(ArrayList<String> rentalID) {
        return Response.builder().data(rentalMapper.getRental(rentalID)).status(100).build();
    }

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
                rentalMapper.updateCollect(user);
                if (i == 1) {
                    return Response.builder().data(null).status(101).build();
                } else {
                    return Response.builder().data(null).status(100).build();
                }
            }
        }
        rentalArrayList.remove(rentalID);
        return Response.builder().message("成功").status(100).build();
    }
    @Override
    public Response getRentalList(Integer offset, Integer limit, Map<String, ArrayList<String>> query) {
        ArrayList<Rental> rentalArrayList;
        if(query.isEmpty() || query==null){
            rentalArrayList = rentalMapper.getAllRentalList(offset,limit);
        }else{
            rentalArrayList = rentalMapper.getAllRentalList(offset,limit);
            // floorPlan
            if (query.containsKey("floorPlan")) {
                ArrayList<String> floorPlans = query.get("floorPlan");
                String floorPlan = floorPlans.get(0);
                rentalList = rentalList.stream().filter(rental -> rental.getFloorPlan().equals(floorPlan));
            }
            // price
            if (query.containsKey("floorPlan")) {
                ArrayList<String> price = query.get("price");
                String highestPrice = price.get(0);
                rentalArrayList = rentalArrayList.stream().filter(rental -> floorPlans.contains(rental -> rental.getPrice().compareTo(highestPrice)<=0));
            }
            // time
            if (query.containsKey("time")) {
            ArrayList<String> times = query.get("time");
            String startTime = times.get(0);
            String endTime = times.get(1);
            rentalArrayList = rentalArrayList.stream().filter(rental -> rental.getStartTime().compareTo(startTime) >= 0 && rental.getEndTime().compareTo(endTime) <= 0);
            }
        }
        return Response.builder().data(rentalArrayList).status(100).build();
    }
    @Override
    public Response postRentalInfo(Rental rentalInfo){
        rentalInfo.setPublishedTime(new Timestamp(new Date().getTime()));
        rentalInfo.setImagesJSON(JSON.toJSONString(rentalInfo.getImages()));
        rentalMapper.postRentalInfo(rentalInfo);
        return Response.builder().message("成功").status(100).build();
    }
}
