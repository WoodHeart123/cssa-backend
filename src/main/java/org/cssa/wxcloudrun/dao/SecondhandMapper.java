package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cssa.wxcloudrun.model.Product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SecondhandMapper {

    ArrayList<Product> getProductList(Integer offset, Integer limit);

    Product getProduct(Integer productID);

    ArrayList<Product> searchProduct(String productTitleFilter, String conditionFilter, String deliveryFilter, Integer offset, Integer limit);

    void saveProduct(Product product);

    void saveContact(Integer userID, String contact);

    void updateSecondHand(Integer userID, Product product);

    List<Product> getUserSecondhand(@Param("userID") Integer userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void deleteSecondHand(Integer userID, Integer productID);



}
