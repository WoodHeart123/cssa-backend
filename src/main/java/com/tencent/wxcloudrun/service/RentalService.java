package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public interface RentalService {

    Response<Object> postRentalInfo(Rental rentalInfo, Boolean save);

    Response<List<Rental>> getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime);

    Response<Object> updateRental(String userID, Rental rentalInfo);
}
