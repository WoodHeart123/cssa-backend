package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.Rental;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RentalMapper {

    ArrayList<Rental> getAllRentalList(Integer offset, Integer limit);

    ArrayList<Rental> getRental(ArrayList<String> rentalID);


}
