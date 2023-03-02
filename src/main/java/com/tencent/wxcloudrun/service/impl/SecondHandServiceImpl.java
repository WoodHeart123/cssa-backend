package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.SecondHandMapper;
import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.SecondHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.RediSearchUtil;

import java.sql.Timestamp;
import java.util.*;


@Service
public class SecondHandServiceImpl implements SecondHandService {

    @Autowired
    SecondHandMapper secondHandMapper;
    @Autowired
    JedisPooled jedisPooled;


    @Override
    public Response getProduct(ArrayList<String> productID) {
        ArrayList<Product> result = secondHandMapper.getProduct(productID);
        for(Product product: result){
            product.setImages(JSON.parseArray(product.getImagesJSON(),String.class));
        }
        return Response.builder().data(result).status(100).build();
    }

    @Override
    public Response getProductList(ProductType productType, Integer offset, Integer limit) {
        ArrayList<Product> productArrayList;
        if(productType == ProductType.ALL){
            productArrayList = secondHandMapper.getAllProductList(offset,limit);
        }else{
            productArrayList = secondHandMapper.getProductList(productType.name(),offset,limit);
        }
        for(Product product: productArrayList){
            product.setImages(JSON.parseArray(product.getImagesJSON(),String.class));
            product.setUTCtime(product.getTime().toInstant().toString());
        }
        return Response.builder().data(productArrayList).status(100).build();
    }

    @Override
    @Transactional
    public Response saveProduct(Product product,Boolean save) {
        product.setImagesJSON(JSON.toJSONString(product.getImages()));
        secondHandMapper.saveProduct(product);
        if(save){
            secondHandMapper.saveContact(product.getUserID(), product.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }

}
