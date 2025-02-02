package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.RentalMapper;
import org.cssa.wxcloudrun.model.Rental;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.ReturnCode;
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
            rentalArrayList = rentalMapper.getRentalListByTime(offset, limit, priceLimit, floorplanList, startTime, endTime);
        } else {
            rentalArrayList = rentalMapper.getRentalList(offset, limit, priceLimit, floorplanList);
        }
        for (Rental rental : rentalArrayList) {
            if (rental.getPublishedTime() == null) {
                rental.setPublishedTime(new Timestamp(0));
            }
            rental.setUTCPublishedTime(rental.getPublishedTime().toInstant().toString());
        }
        return new Response<>(rentalArrayList);
    }

    @Override
    public Response<Rental> getRental(Integer rentalId) {
        Rental rental = rentalMapper.getRental(rentalId);
        if(rental == null){
            return new Response<>(ReturnCode.NO_SEARCH_RESULT);
        }
        if (rental.getPublishedTime() == null) {
            rental.setPublishedTime(new Timestamp(0));
        }
        rental.setUTCPublishedTime(rental.getPublishedTime().toInstant().toString());
        return new Response<>(rental);
    }

    @Override
    public Response<Object> updateRental(Integer userID, Rental rentalInfo) {
        rentalMapper.updateRental(userID, rentalInfo);
        return new Response();
    }

    @Override
    @Transactional
    public Response<Object> postRentalInfo(Rental rentalInfo, Boolean save) {
        rentalInfo.setPublishedTime(new Timestamp(new Date().getTime()));
        rentalMapper.postRentalInfo(rentalInfo);
        if (save) {
            rentalMapper.saveContact(rentalInfo.getUserID(), rentalInfo.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }

    @Override
    public Response<Object> deleteRental(Integer userID, Integer rentalID) {
        rentalMapper.deleteRental(userID, rentalID);
        return new Response<>();
    }


    @Override
    public Response<List<Rental>> getUserRental(Integer userID, Integer offset, Integer limit) {
        ArrayList<Rental> rentalArrayList = rentalMapper.getUserRental(userID, offset, limit);
        for (Rental rental : rentalArrayList) {
            if (rental.getPublishedTime() == null) {
                rental.setPublishedTime(new Timestamp(0));
            }
            rental.setUTCPublishedTime(rental.getPublishedTime().toInstant().toString());
        }
        return new Response<>(rentalArrayList);
    }

}
