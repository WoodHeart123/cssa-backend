package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.ProductType;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.SecondHandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping({"/secondhand"})
public class SecondHandController {

    @Autowired
    SecondHandService secondHandService;

    @RequestMapping(value= {"/getProductList"}, method = {RequestMethod.GET})
    public Response getProductList(@RequestParam ProductType productType, @RequestParam Integer offset, @RequestParam Integer limit, HttpServletRequest request){
        return secondHandService.getProductList(productType,offset,limit);
    }
}
