package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface SecondhandService {

    /**
     * @param productType product type
     * @param offset      offset in sql
     * @param limit       limit of list size
     * @return list of product information
     */
    Response<List<Product>> getProductList(Integer offset, Integer limit);

    Response<Object> saveProduct(Product product, Boolean save);

    Response<List<Product>> getProduct(ArrayList<String> productID);

    Response<Object> updateSecondHand(String userID, Product product);


}
