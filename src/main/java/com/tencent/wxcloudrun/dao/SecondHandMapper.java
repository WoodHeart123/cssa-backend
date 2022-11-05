package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SecondHandMapper {

    ArrayList<Product> getProductList(String productType, Integer offset, Integer limit);

    ArrayList<Product> getAllProductList(Integer offset, Integer limit);
    
    boolean collect(Product product);
    boolean cancelCollect(Product product);

    ArrayList<Product> getProduct(ArrayList<String> productID);


}
