package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.ReturnCode;
import com.tencent.wxcloudrun.service.SecondhandService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping({"/secondhand"})
@Api(tags = "二手中心")
public class SecondhandController {

    @Autowired
    SecondhandService secondhandService;

    @Autowired
    private JedisPooled jedisPooled;


    @RequestMapping(value = {"/suggest"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "搜索建议", description = "搜索建议")
    public Response<List<String>> suggest(@Parameter(description = "用户输入字符") @RequestParam String value) {
        return new Response<>(jedisPooled.ftSugGet("productName", value, true, 50));
    }

    @RequestMapping(value = {"/search"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "搜索", description = "搜索")
    public Response<List<Product>> search(@Parameter(description = "用户输入的搜索字符") @RequestParam String value,
                                          @RequestParam Integer limit,
                                          @RequestParam Integer offset){
        ArrayList<String> productIDList = new ArrayList<>();
        Query q = new Query("*" + value + "*").setLanguage("chinese").limit(offset, limit);
        SearchResult sr = jedisPooled.ftSearch("product-index", q);
        for (Document document : sr.getDocuments()) {
            productIDList.add(document.getString("productID"));
        }
        if (productIDList.size() == 0) {
            return new Response<>(ReturnCode.NO_SEARCH_RESULT);
        }
        return secondhandService.getProduct(productIDList);

    }

    @RequestMapping(value = {"/getProductList"}, method = {RequestMethod.GET})
    @Operation(summary = "获取商品列表", description = "获取商品列表")
    public Response<List<Product>> getProductList(@Parameter(description = "商品类型") @RequestParam ProductType productType,
                                                  @RequestParam Integer offset,
                                                  @RequestParam Integer limit) {
        return secondhandService.getProductList(productType, offset, limit);
    }

    @RequestMapping(value = {"/saveProduct"}, method = {RequestMethod.POST})
    @Operation(summary = "发布商品", description = "发布商品")
    public Response<Object> saveProduct(@Parameter(description = "是否保存联系方式") @RequestParam Boolean save,
                                        @Parameter(description = "商品信息") @RequestBody Product product,
                                        @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        product.setUserID(openid);
        secondhandService.saveProduct(product, save);
        Map<String, Object> fields = new HashMap<>();
        fields.put("productID", product.getProductID());
        fields.put("productName", product.getProductTitle());
        jedisPooled.hset("product:" + product.getProductID().toString(), RediSearchUtil.toStringMap(fields));
        jedisPooled.ftSugAdd("productName", product.getProductTitle(), 1.0);
        return new Response<>();
    }

    @RequestMapping(value = {"/updateSecondHand"}, method = {RequestMethod.POST})
    @Operation(summary = "更新商品", description = "更新商品")
    public Response<Object> updateSecondHand(@Parameter(description = "商品信息") @RequestBody Product product,
                                             @Parameter(description = "微信ID")  @RequestHeader("x-wx-openid") String openid) {
        return secondhandService.updateSecondHand(openid, product);
    }


}
