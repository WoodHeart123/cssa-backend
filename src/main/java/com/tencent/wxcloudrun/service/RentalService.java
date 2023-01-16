package com.tencent.wxcloudrun.service;

import org.springframework.stereotype.Service;

@Service
public interface RentalService {
  Response saveRentalInfo(Integer productID, boolean save, String userID);
  Response postRentalInfo(Rental rentalInfo);
  Response getRentalList(Integer offset, Integer limit, Map<String, ArrayList<String>> query);
}
