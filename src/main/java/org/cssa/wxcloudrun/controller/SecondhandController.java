package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.SecondhandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/secondhand"})
@Tag(name = "二手中心")
public class SecondhandController {

    @Autowired
    SecondhandService secondhandService;

    @RequestMapping(value = {"/getProductList"}, method = {RequestMethod.GET})
    @Operation(summary = "获取商品列表", description = "获取商品列表")
    public Response<List<Product>> getProductList(@RequestParam Integer offset,
                                                  @RequestParam Integer limit) {
        return secondhandService.getProductList(offset, limit);
    }

    @RequestMapping(value = {"/saveProduct"}, method = {RequestMethod.POST})
    @Operation(summary = "发布商品", description = "发布商品")
    public Response<Object> saveProduct(@Parameter(description = "是否保存联系方式") @RequestParam Boolean save,
                                        @Parameter(description = "商品信息") @RequestBody Product product,
                                        @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        product.setUserID(openid);
        secondhandService.saveProduct(product, save);
        return new Response<>();
    }

    @RequestMapping(value = {"/updateSecondHand"}, method = {RequestMethod.POST})
    @Operation(summary = "更新商品", description = "更新商品")
    public Response<Object> updateSecondHand(@Parameter(description = "商品信息") @RequestBody Product product,
                                             @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return secondhandService.updateSecondHand(openid, product);
    }


}
