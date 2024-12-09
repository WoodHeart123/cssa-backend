package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    Response<Product> getProduct(Integer productID);

    Response<Object> saveProduct(Product product, Boolean save);


    Response<Object> updateSecondHand(String userID, Product product);


}
