package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Rental;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public interface FindMateService {
    /**
     * @param productType product type
     * @param offset      offset in sql
     * @param limit       limit of list size
     * @return list of product information
     */
    Response<Object> saveMate(Mate mate, Boolean save);

}
