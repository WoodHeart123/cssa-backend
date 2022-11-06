package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SecondHandMapper {

    ArrayList<Product> getProductList(String productType, Integer offset, Integer limit);

    ArrayList<Product> getAllProductList(Integer offset, Integer limit);
    
    void save(Product product);
    
    boolean collect(Product product);
    boolean cancelCollect(Product product);
    
    
}
