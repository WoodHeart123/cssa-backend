package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.stereotype.Service;


@Service
public class RentalServiceImpl implements RentalService {
     @Override
    public Response saveRentalInfo(Integer rentalID, boolean save, String userID) {
        User user = RentalMapper.collect(userID);
        List<Integer> rentalArrayList = JSON.parseArray(user.getSavedRentalJSON(), Integer.class);
        if(save){
          int i = 0;
          if(!(rentalArrayList.contains(rentalID))){
              i = 1;
              rentalArrayList.add(rentalID);
              String json = JSON.toJSONString(rentalArrayList);
              user.setSavedRentalJSON(json);
              RentalMapper.updateCollect(user);
              if (i == 1) {
                  return Response.builder().data(null).status(101).build();
              } else {
                  return Response.builder().data(null).status(100).build();
              }
          }
        }
        if(rentalArrayList.contains(rentalID)){
           rentalArrayList.remove(rentalID);
        }
        
    }
}
