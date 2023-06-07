package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.RentalMapper;
import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalMapper rentalMapper;


    @Override
    public Response getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime) {
        ArrayList<Rental> rentalArrayList;
        if (!startTime.equals(new Timestamp(0))) {
            rentalArrayList = rentalMapper.getRentalTimed(offset, limit, priceLimit, floorplanList, startTime, endTime);
        } else {
            rentalArrayList = rentalMapper.getRental(offset, limit, priceLimit, floorplanList);
        }
        for (Rental rental : rentalArrayList) {
            rental.setImages((ArrayList<String>) JSON.parseArray(rental.getImagesJSON(), String.class));
            if (rental.getPublishedTime() == null) {
                rental.setPublishedTime(new Timestamp(0));
            }
            rental.setUTCPublishedTime(rental.getPublishedTime().toInstant().toString());
        }
        return Response.builder().data(rentalArrayList).status(100).build();
    }

    @Override
    public Response updateRental(String userID, Rental rentalInfo) {
        rentalMapper.updateRental(userID, rentalInfo);
        return new Response();
    }

    @Override
    @Transactional
    public Response postRentalInfo(Rental rentalInfo, Boolean save) {
        rentalInfo.setPublishedTime(new Timestamp(new Date().getTime()));
        rentalInfo.setImagesJSON(JSON.toJSONString(rentalInfo.getImages()));
        rentalMapper.postRentalInfo(rentalInfo);
        if (save) {
            rentalMapper.saveContact(rentalInfo.getUserID(), rentalInfo.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }

}
