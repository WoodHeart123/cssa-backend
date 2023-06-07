package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface SecondhandService {

    /**
     * @param productType product type
     * @param offset      offset in sql
     * @param limit       limit of list size
     * @return list of product information
     */
    Response getProductList(ProductType productType, Integer offset, Integer limit);

    Response saveProduct(Product product, Boolean save);

    Response getProduct(ArrayList<String> productID);

    Response updateSecondHand(String userID, Product product);


}
