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
        return Response.builder().data(rentalMapper.getRentalByID(rentalID)).status(100).build();
    }

    @Override
    public Response saveRentalInfo(Integer rentalID, boolean save, String userID) {
        User user = rentalMapper.collect(userID);
        List<Integer> rentalArrayList = JSON.parseArray(user.getSavedRentalJSON(), Integer.class);
        if(save){
            if(!(rentalArrayList.contains(rentalID))){
                rentalArrayList.add(rentalID);
            }
        }else{
            rentalArrayList.remove(rentalID);
        }
        user.setSavedRentalJSON(JSON.toJSONString(rentalArrayList));
        rentalMapper.updateCollect(user);
        return Response.builder().message("成功").status(100).build();
    }
    @Override
    public Response getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime) {
        ArrayList<Rental> rentalArrayList;
        if(!startTime.equals(new Timestamp(0))){
            rentalArrayList = rentalMapper.getRentalTimed(offset,limit,priceLimit, floorplanList, startTime, endTime);
            // floorPlan
        }else{
            // TODO: 获取没有时间限制的租房列表
            rentalArrayList = rentalMapper.getRental(offset,limit,priceLimit, floorplanList);
            for(Rental rental : rentalArrayList){
                //rental.setImages(JSON.parseArray(rental.getImagesJSON(),String.class));
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
    @Override
    public Response collect(Integer rentalID, String userID) {
        User user = RentalMapper.collect(userID);
        List<Integer> rentalArrayList = JSON.parseArray(user.getSavedRentalJSON(), Integer.class);
        int i = 0;
        if(!(rentalArrayList.contains(rentalID))){
            i = 1;
            rentalArrayList.add(rentalID);
            String json = JSON.toJSONString(rentalArrayList);
            user.setSavedRentalJSON(json);
           Rental.Mapper.updateCollect(user);
        }
        if (i == 1) {
            return Response.builder().data(null).status(101).build();
        } else {
            return Response.builder().data(null).status(100).build();
        }
    }
}
