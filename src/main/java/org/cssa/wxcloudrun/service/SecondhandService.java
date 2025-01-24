package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SecondhandService {

    Response<List<Product>> searchProduct(String productTitleFilter, String conditionFilter, String deliveryFilter, Integer offset, Integer limit);

    Response<Product> getProduct(Integer productID);

    Response<Product> saveProduct(Product product, Boolean save);


    Response<Object> updateSecondHand(Integer userID, Product product);

    Response<List<Product>> getUserSecondhand(Integer userID, Integer offset, Integer limit);

    Response<Object> deleteUserSecondHand(Integer userID, Integer productID);


}
