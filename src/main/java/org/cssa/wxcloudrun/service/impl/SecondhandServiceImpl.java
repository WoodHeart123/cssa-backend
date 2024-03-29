package org.cssa.wxcloudrun.service.impl;

import org.cssa.wxcloudrun.dao.SecondhandMapper;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.SecondhandService;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class SecondhandServiceImpl implements SecondhandService {

    @Autowired
    SecondhandMapper secondhandMapper;

    @Override
    public Response<List<Product>> getProduct(ArrayList<String> productID) {
        ArrayList<Product> result = secondhandMapper.getProduct(productID);
        for (Product product : result) {
            product.setImages(JSON.parseArray(product.getImagesJSON(), String.class));
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return new Response<>(result);
    }

    @Override
    public Response<List<Product>> getProductList(Integer offset, Integer limit) {
        ArrayList<Product> productArrayList;
        productArrayList = secondhandMapper.getProductList(offset, limit);
        for (Product product : productArrayList) {
            product.setImages(JSON.parseArray(product.getImagesJSON(), String.class));
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return new Response<>(productArrayList);
    }

    @Override
    @Transactional
    public Response<Object> saveProduct(Product product, Boolean save) {
        product.setImagesJSON(JSON.toJSONString(product.getImages()));
        secondhandMapper.saveProduct(product);
        if (save) {
            secondhandMapper.saveContact(product.getUserID(), product.getContact());
        }
        return new Response<>();
    }

    @Override
    @Transactional
    public Response<Object> updateSecondHand(String userID, Product product) {
        secondhandMapper.updateSecondHand(userID, product);
        return new Response<>();
    }


}
