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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SecondHandServiceImpl implements SecondHandService {

    @Autowired
    SecondHandMapper secondHandMapper;


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
        }
        return Response.builder().data(productArrayList).status(100).build();
    }

    @Override
    @Transactional
    public Response saveProduct(Product product,Boolean save) {
        product.setTime(new Timestamp(new Date().getTime()));
        product.setImagesJSON(JSON.toJSONString(product.getImages()));
        secondHandMapper.saveProduct(product);
        if(save){
            secondHandMapper.saveContact(product.getUserID(), product.getContact());
        }
        return Response.builder().message("成功").status(100).build();
    }
    
    @Override
    public Response collect(Integer productID, String userID) {
        User user = secondHandMapper.collect(userID);
        List<Integer> productArrayList = JSON.parseArray(user.getSavedProductJSON(), Integer.class);
        int i = 0;
        if(!(productArrayList.contains(productID))){
            i = 1;
            productArrayList.add(productID);
            String json = JSON.toJSONString(productArrayList);
            user.setSavedProductJSON(json);
           secondHandMapper.updateCollect(user);
        }
        if (i == 1) {
            return Response.builder().data(null).status(101).build();
        } else {
            return Response.builder().data(null).status(100).build();
        }
    }

}
