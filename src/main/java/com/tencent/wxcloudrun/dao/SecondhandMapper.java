package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface SecondhandMapper {

    ArrayList<Product> getProductList(String productType, Integer offset, Integer limit);

    ArrayList<Product> getProduct(ArrayList<String> productID);

    ArrayList<Product> getAllProductList(Integer offset, Integer limit);
    
    User collect(String userID);
    void saveProduct(Product product);
    void saveContact(String userID,String contact);
    void updateSecondHand(String userID, Product product);

}
