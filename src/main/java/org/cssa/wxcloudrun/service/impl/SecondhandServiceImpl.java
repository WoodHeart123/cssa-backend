package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.SecondhandMapper;
import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.ReturnCode;
import org.cssa.wxcloudrun.service.SecondhandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class SecondhandServiceImpl implements SecondhandService {

    @Autowired
    SecondhandMapper secondhandMapper;

    @Override
    public Response<List<Product>> searchProduct(String productTitleFilter, String conditionFilter, String deliveryFilter, Integer offset, Integer limit) {
        ArrayList<Product> productArrayList;
        productArrayList = secondhandMapper.searchProduct(productTitleFilter, conditionFilter, deliveryFilter, offset, limit);
        for (Product product : productArrayList) {
            product.setUTCtime(product.getTime().toInstant().toString());
            if(product.getSeller() != null){
                product.setSellerAvatar(product.getSeller().getAvatar());
                product.setSellerNickname(product.getSeller().getNickname());
            }
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
    public Response<Product> saveProduct(Product product, Boolean save) {

        secondhandMapper.saveProduct(product);
        if (save) {
            secondhandMapper.saveContact(product.getUserID(), product.getContact());
        }
        product.setTime(Timestamp.from(Instant.now()));
        product.setUTCtime(product.getTime().toInstant().toString());
        return new Response<>(product);
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