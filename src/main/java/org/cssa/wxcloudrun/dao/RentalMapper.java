package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.Rental;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface RentalMapper {

    ArrayList<Rental> getRentalTimed(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime);

    ArrayList<Rental> getRental(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList);

    void postRentalInfo(Rental rental);

    void saveContact(String userID, String contact);

    void updateRental(String userID, Rental rental);


}
