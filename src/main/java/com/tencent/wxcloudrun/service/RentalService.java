package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Service
public interface RentalService {

    Response postRentalInfo(Rental rentalInfo, Boolean save);
    Response getRentalList(Integer offset, Integer limit, Integer priceLimit, ArrayList<String> floorplanList, Timestamp startTime, Timestamp endTime);
    Response getRental(ArrayList<String> productID);
}
