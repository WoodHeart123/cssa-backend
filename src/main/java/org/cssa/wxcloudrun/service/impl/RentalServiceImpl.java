package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.RentalMapper;
import org.cssa.wxcloudrun.model.Rental;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.RentalService;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalMapper rentalMapper;


    @Override
    public Response<List<Rental>> getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime) {
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
        return new Response<>(rentalArrayList);
    }

    @Override
    public Response<Object> updateRental(String userID, Rental rentalInfo) {
        rentalMapper.updateRental(userID, rentalInfo);
        return new Response();
    }

    @Override
    @Transactional
    public Response<Object> postRentalInfo(Rental rentalInfo, Boolean save) {
        rentalInfo.setPublishedTime(new Timestamp(new Date().getTime()));
        rentalInfo.setImagesJSON(JSON.toJSONString(rentalInfo.getImages()));
        rentalMapper.postRentalInfo(rentalInfo);
        if (save) {
            rentalMapper.saveContact(rentalInfo.getUserID(), rentalInfo.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }

}
