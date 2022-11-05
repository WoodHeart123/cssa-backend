package com.tencent.wxcloudrun.controller;


import com.github.twohou.sonic.ChannelFactory;
import com.github.twohou.sonic.SearchChannel;
import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.service.SecondHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping({"/secondhand"})
public class SecondHandController {

    @Autowired
    SecondHandService secondHandService;

    @Autowired
    private ChannelFactory channelFactory;

    @RequestMapping(value={ "/suggest"}, method = {RequestMethod.GET})
    public Response suggest(@RequestParam String value, HttpServletRequest request) throws IOException {
        SearchChannel search = channelFactory.newSearchChannel();
        search.ping();
        return secondHandService.getProductName(search.suggest("product","default", value));
    }

    @RequestMapping(value={ "/search"}, method = {RequestMethod.GET})
    public Response search(@RequestParam String value, HttpServletRequest request) throws IOException {
        SearchChannel search = channelFactory.newSearchChannel();
        search.ping();
        return secondHandService.getProduct(search.query("product","default", value));

    }

    @RequestMapping(value= {"/getProductList"}, method = {RequestMethod.GET})
    public Response getProductList(@RequestParam ProductType productType, @RequestParam Integer offset, @RequestParam Integer limit, HttpServletRequest request){
        return secondHandService.getProductList(productType,offset,limit);
    }

    @RequestMapping(value= {"/collect"}, method = {RequestMethod.GET})
    public Response cancelCollect(@RequestParam Product product, HttpServletRequest request){
        return secondHandService.collect(product);
    }
    
    @RequestMapping(value= {"/collect"}, method = {RequestMethod.GET})
    public Response post(@RequestParam Product product, HttpServletRequest request){
        return secondHandService.post(product);
    }

//    @RequestMapping(value= {"/collect"}, method = {RequestMethod.GET})
//    public Response collect(@RequestParam Product product, HttpServletRequest request){
//        return secondHandService.collect(product);
//    }
}
