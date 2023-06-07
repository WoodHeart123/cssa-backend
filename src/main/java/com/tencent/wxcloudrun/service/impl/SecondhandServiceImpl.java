package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.SecondhandMapper;
import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.SecondhandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPooled;

import java.util.ArrayList;


@Service
public class SecondhandServiceImpl implements SecondhandService {

    @Autowired
    SecondhandMapper secondhandMapper;
    @Autowired
    JedisPooled jedisPooled;


    @Override
    public Response getProduct(ArrayList<String> productID) {
        ArrayList<Product> result = secondhandMapper.getProduct(productID);
        for (Product product : result) {
            product.setImages(JSON.parseArray(product.getImagesJSON(), String.class));
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return Response.builder().data(result).status(100).build();
    }

    @Override
    public Response getProductList(ProductType productType, Integer offset, Integer limit) {
        ArrayList<Product> productArrayList;
        if (productType == ProductType.ALL) {
            productArrayList = secondhandMapper.getAllProductList(offset, limit);
        } else {
            productArrayList = secondhandMapper.getProductList(productType.name(), offset, limit);
        }
        for (Product product : productArrayList) {
            product.setImages(JSON.parseArray(product.getImagesJSON(), String.class));
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return Response.builder().data(productArrayList).status(100).build();
    }

    @Override
    @Transactional
    public Response saveProduct(Product product, Boolean save) {
        product.setImagesJSON(JSON.toJSONString(product.getImages()));
        secondhandMapper.saveProduct(product);
        if (save) {
            secondhandMapper.saveContact(product.getUserID(), product.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }

    @Override
    @Transactional
    public Response updateSecondHand(String userID, Product product) {
        secondhandMapper.updateSecondHand(userID, product);
        return new Response();
    }


}
