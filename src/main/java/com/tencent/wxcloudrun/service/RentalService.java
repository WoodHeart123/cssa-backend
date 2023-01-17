package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface RentalService {
    Response saveRentalInfo(Integer productID, boolean save, String userID);
    Response postRentalInfo(Rental rentalInfo);
    Response getRentalList(Integer offset, Integer limit, Map<String, ArrayList<String>> query);
    Response getRental(ArrayList<String> productID);
}
