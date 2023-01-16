package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.RentalMapper;
import com.tencent.wxcloudrun.dao.SecondHandMapper;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    RentalMapper rentalMapper;


    @Override
    public Response getRental(ArrayList<String> rentalID) {
        return Response.builder().data(rentalMapper.getRental(rentalID)).status(100).build();
    }
}
