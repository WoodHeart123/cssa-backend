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
import redis.clients.jedis.search.SearchResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

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
    public Response search(@RequestParam String value, HttpServletRequest request) throws IOException {
        ArrayList<String> productIDList = new ArrayList<>();
        Query q = new Query("*" + value + "*").setLanguage("chinese");
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
    public Response getProductList(@RequestParam ProductType productType, @RequestParam Integer offset, @RequestParam Integer limit, HttpServletRequest request){
        return secondHandService.getProductList(productType,offset,limit);
    }
    
    @RequestMapping(value= {"/saveProduct"}, method = {RequestMethod.GET})
        public Response saveProduct(@RequestParam Product product, HttpServletRequest request){
        return secondHandService.saveProduct(product);
    }

//    @RequestMapping(value= {"/collect"}, method = {RequestMethod.GET})
//    public Response cancelCollect(@RequestParam Integer productID, String UserID, HttpServletRequest request){
//        return secondHandService.collect(product,UserID);
//    }
}
