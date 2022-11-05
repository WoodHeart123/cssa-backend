package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.SecondHandMapper;
import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.SecondHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scripting.support.RefreshableScriptTargetSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class SecondHandServiceImpl implements SecondHandService {

    @Autowired
    SecondHandMapper secondHandMapper;
    ArrayList<Product> collection = new ArrayList<Product>();
    @Override
    public Response getProductList(ProductType productType, Integer offset, Integer limit) {
        ArrayList<Product> productArrayList;
        if(productType == ProductType.ALL){
            productArrayList = secondHandMapper.getAllProductList(offset,limit);
        }else{
            productArrayList = secondHandMapper.getProductList(productType.name(),offset,limit);
        }
        return Response.builder().data(productArrayList).status(100).build();
    }

    @Override
    public Response savePost(Product product) {
        Post post = new Post();
        ArrayList<Product> productArrayList;
        if(product.productDescription!=NULL){
            post.setProductDescription(product.productDescription);
        }
        post.setPrice(product.price);
        post.setCondition(product.condition);
        post.setProductType(product.productType);
        post.setCondition(product.condition);
        post.setProductTitle(product.productTitle);
        post.setContact(product.contact);
        post.setDelivery(product.delivery);
        productArrayList.add(product);
        return Response.builder().data(product).status(100).build();
     
    }

    @Override
    public Response collect(Product product) {
        if(!(collection.contains(product))){
            collection.add(product);
        }
        return Response.builder().data(null).status(100).build();
    }
    @Override
    public Response cancelCollect(Product product) {
        if(collection.contains(product)){
            collection.remove(product);
        }
        return Response.builder().data(null).status(100).build();
    }
}
