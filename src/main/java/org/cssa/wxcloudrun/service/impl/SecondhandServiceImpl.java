package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.SecondhandMapper;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.ReturnCode;
import org.cssa.wxcloudrun.service.SecondhandService;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class SecondhandServiceImpl implements SecondhandService {

    @Autowired
    SecondhandMapper secondhandMapper;

    @Override
    public Response<List<Product>> getProductList(Integer offset, Integer limit) {
        ArrayList<Product> productArrayList;
        productArrayList = secondhandMapper.getProductList(offset, limit);
        for (Product product : productArrayList) {
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return new Response<>(productArrayList);
    }

    @Override
    public Response<Product> getProduct(Integer productID) {
        Product product = secondhandMapper.getProduct(productID);
        if(product == null){
            return new Response<>(ReturnCode.NO_SEARCH_RESULT);
        }
        product.setUTCtime(product.getTime().toInstant().toString());
        return new Response<>(product);
    }

    @Override
    @Transactional
    public Response<Object> saveProduct(Product product, Boolean save) {
        secondhandMapper.saveProduct(product);
        if (save) {
            secondhandMapper.saveContact(product.getUserID(), product.getContact());
        }
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> updateSecondHand(Integer userID, Product product) {
        secondhandMapper.updateSecondHand(userID, product);
        return new Response<>();
    }

    @Override
    public Response<List<Product>> getUserSecondhand(Integer userID, Integer offset, Integer limit) {
        List<Product> productList = secondhandMapper.getUserSecondhand(userID, offset, limit);
        for (Product product : productList) {
            if (product.getTime() == null) {
                product.setTime(new Timestamp(0));
            }
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return new Response<>(productList);
    }

    @Override
    @Transactional
    public Response<Object> deleteUserSecondHand(Integer userID, Integer productID) {
        secondhandMapper.deleteSecondHand(userID, productID);
        return new Response<>();
    }


}
