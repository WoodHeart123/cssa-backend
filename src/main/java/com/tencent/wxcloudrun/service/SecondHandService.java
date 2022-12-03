package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface SecondHandService {

    /**
     * @param productType product type
     * @param offset offset in sql
     * @param limit limit of list size
     * @return list of product information
     */
    Response getProductList(ProductType productType,Integer offset, Integer limit);
    
    Response saveProduct(Product product,Boolean save, String userID);
    
    Response collect(Integer productID, String UserID);

    Response getProduct(ArrayList<String> productID);
    
}
