package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface RentalMapper {

    ArrayList<Rental> getAllRentalList(Integer offset, Integer limit);

    ArrayList<Rental> getRental(ArrayList<String> rentalID);

    ArrayList<Rental> getRentalTimed(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime);
    User collect(String userID);
    void updateCollect(User user);
    void postRentalInfo(Rental rental);


}
