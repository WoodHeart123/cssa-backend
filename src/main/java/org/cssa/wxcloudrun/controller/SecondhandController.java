package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.SecondhandService;
import org.cssa.wxcloudrun.service.WeChatAPI;
import org.cssa.wxcloudrun.util.InjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping({"/secondhand"})
@Tag(name = "二手中心")
public class SecondhandController {

    @Autowired
    SecondhandService secondhandService;

    @Autowired
    WeChatAPI weChatAPI;

    @Deprecated
    @RequestMapping(value = {"/getProductList"}, method = {RequestMethod.GET})
    @Operation(summary = "获取商品列表", description = "获取商品列表")
    public Response<List<Product>> getProductList(@RequestParam Integer offset,
                                                  @RequestParam Integer limit) {
        return secondhandService.getProductList(offset, limit);
    }

    @RequestMapping(value = {"/searchProduct"}, method = {RequestMethod.GET})
    @Operation(summary = "通过条件筛选商品列表", description = "通过条件筛选商品列表")
    public Response<List<Product>> searchProduct(@RequestParam Optional<String> productTitleFilter,
                                                     @RequestParam Optional<String> conditionFilter,
                                                     @RequestParam Optional<String> deliveryFilter,
                                                     @RequestParam Integer offset,
                                                     @RequestParam Integer limit) {
        return secondhandService.searchProduct(productTitleFilter.orElse(""), conditionFilter.orElse("ALL").toUpperCase(),
                deliveryFilter.orElse("ALL").toUpperCase(), offset, limit);
    }

    @RequestMapping(value = {"/getProduct"}, method = {RequestMethod.GET})
    @Operation(summary = "获取特定商品信息", description = "获取特定商品信息")
    public Response<Product> getProduct(@RequestParam Integer productID) {
        return secondhandService.getProduct(productID);
    }

    @RequestMapping(value = {"/saveProduct"}, method = {RequestMethod.POST})
    @Operation(summary = "发布商品", description = "发布商品")
    public Response<Product> saveProduct(@Parameter(description = "是否保存联系方式") @RequestParam Boolean save,
                                        @Parameter(description = "商品信息") @RequestBody Product product,
                                        @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        WechatResponse wechatResponse = weChatAPI.MsgCheck(product.getProductTitle() + ";" + product.getProductDescription(), user.getOpenID(), 3);
        if(wechatResponse.getResult() != null && wechatResponse.getResult().getLabel() >= 20000){
            return new Response<>(ReturnCode.CENSORED_UGC_CONTENT);
        }
        product.setUserID(user.getId());
        return secondhandService.saveProduct(product, save);
    }

    @RequestMapping(value = {"/updateSecondHand"}, method = {RequestMethod.POST})
    @Operation(summary = "更新商品", description = "更新商品")
    public Response<Object> updateSecondHand(@Parameter(description = "商品信息") @RequestBody Product product,
                                             @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        return secondhandService.updateSecondHand(user.getId(), product);
    }

    @RequestMapping(value = {"/deleteSecondhand"}, method = RequestMethod.DELETE)
    public Response<Object> deleteUserSecondhand(@RequestParam Integer productID,
                                         @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        return secondhandService.deleteUserSecondHand(user.getId(), productID);
    }

    @RequestMapping(value = {"/getUserSecondhandList"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Product>> getUserSecondhand(@RequestParam Integer offset,
                                      @RequestParam Integer limit,
                                      @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
            System.out.println(user.getId());
            return secondhandService.getUserSecondhand(user.getId(), offset, limit);
    }

    @RequestMapping(value = {"/deleteUserSecondhand"}, method = RequestMethod.DELETE)
    public Response<Object> deleteUserSecondhand(@RequestParam Integer productID,
                                         @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        return secondhandService.deleteUserSecondHand(user.getId(), productID);
    }

    @RequestMapping(value = {"/getUserSecondhandList"}, method = {RequestMethod.GET}, produces = "application/json")
    public Response<List<Product>> getUserSecondhand(@RequestParam Integer offset,
                                      @RequestParam Integer limit,
                                      @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
            System.out.println(user.getId());
            return secondhandService.getUserSecondhand(user.getId(), offset, limit);
    };


}