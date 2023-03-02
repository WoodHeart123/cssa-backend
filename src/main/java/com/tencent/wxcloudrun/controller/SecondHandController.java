package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.service.SecondHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.RediSearchUtil;
import redis.clients.jedis.search.SearchResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping({"/secondhand"})
public class SecondHandController {

    @Autowired
    SecondHandService secondHandService;

    @Autowired
    private JedisPooled jedisPooled;


    @RequestMapping(value={ "/suggest"}, method = {RequestMethod.GET})
    public Response suggest(@RequestParam String value, HttpServletRequest request) throws IOException {
        return Response.builder().data(jedisPooled.ftSugGet("productName", value, true, 50)).status(100).build();
    }

    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, @RequestParam Integer limit, @RequestParam Integer offset) throws IOException {
        ArrayList<String> productIDList = new ArrayList<>();
        Query q = new Query("*" + value + "*").setLanguage("chinese").limit(offset, limit);
        SearchResult sr = jedisPooled.ftSearch("product-index",q);
        for(Document document: sr.getDocuments()){
           productIDList.add(document.getString("productID"));
        }
        if(productIDList.size() == 0){
            return Response.builder().message("没有匹配结果").status(124).build();
        }
        return secondHandService.getProduct(productIDList);

    }

    @RequestMapping(value= {"/getProductList"}, method = {RequestMethod.GET})
    public Response getProductList(@RequestParam ProductType productType, @RequestParam Integer offset, @RequestParam Integer limit){
        return secondHandService.getProductList(productType,offset,limit);
    }
    
    @RequestMapping(value= {"/saveProduct"}, method = {RequestMethod.POST})
        public Response saveProduct(@RequestParam Boolean save,@RequestBody Product product, @RequestHeader("x-wx-openid") String openid) {
        product.setUserID(openid);
        secondHandService.saveProduct(product, save);
        Map<String, Object> fields = new HashMap<>();
        fields.put("productID", product.getProductID());
        fields.put("productName", product.getProductTitle());
        jedisPooled.hset("product:" + product.getProductID().toString(), RediSearchUtil.toStringMap(fields));
        jedisPooled.ftSugAdd("productName", product.getProductTitle(), 1.0);
        return new Response();
    }

}
