package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.User;

import java.util.ArrayList;

@Mapper
public interface SecondhandMapper {

    ArrayList<Product> getProductList(Integer offset, Integer limit);

    ArrayList<Product> getProduct(ArrayList<String> productID);

    ArrayList<Product> getAllProductList(Integer offset, Integer limit);

    User collect(String userID);

    void saveProduct(Product product);

    void saveContact(String userID, String contact);

    void updateSecondHand(String userID, Product product);

}
