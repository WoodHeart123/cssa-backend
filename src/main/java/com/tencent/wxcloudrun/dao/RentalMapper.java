package com.tencent.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RentalMapper {
  User collect(String userID);
  void updateCollect(User user);
  void saveRental(Rental rental);
}
