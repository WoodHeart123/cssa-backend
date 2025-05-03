package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.Rental;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface RentalMapper {

    ArrayList<Rental> getRentalListByTime(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime);

    ArrayList<Rental> getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList);

    Rental getRental(Integer rentalId);
    void postRentalInfo(Rental rental);

    void saveContact(Integer userID, String contact);

    void updateRental(Integer userID, Rental rental);

    void deleteRental(Integer userID, Integer rentalID);

    ArrayList<Rental> getUserRental(Integer userID, Integer offset, Integer limit);


}
