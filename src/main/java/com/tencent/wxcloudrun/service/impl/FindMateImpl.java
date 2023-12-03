package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.RentalMapper;
import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class FindMateImpl implements FindMateService {
    @Autowired
    FindMateMapper FindMateMapper;

    @Override
    @Transactional
    public Response<Object> saveProduct(Mate mate, Boolean save) {
        mate.setImagesJSON(JSON.toJSONString(mate.getImages()));
        FindMateMapper.saveProduct(mate);
        if (save) {
            FindMateMapper.saveContact(mate.getUserID(), mate.getContact());
        }
        return new Response<>();
    }
}
