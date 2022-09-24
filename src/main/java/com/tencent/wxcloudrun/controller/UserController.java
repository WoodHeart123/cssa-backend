package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.CacheStore;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    CacheStore<Integer> authCodeCache;

    @Autowired
    UserService userService;


    @RequestMapping(value={"/getAuthCode"}, method = {RequestMethod.GET})
    public Response getAuthCode(@RequestParam String email, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        Random ran = new Random();
        int authCode = ran.nextInt(1000000);
        authCodeCache.add(openid.get(),authCode);
        userService.getAuthCode(email,authCode);
        return Response.builder().status(100).build();
    }

    @RequestMapping(value={"/verifyAuthCode"}, method = {RequestMethod.GET})
    public Response verifyAuthCod(@RequestParam Integer authCode, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        if(authCodeCache.get(openid.get()) != null && Objects.equals(authCodeCache.get(openid.get()), authCode)){
            return userService.authSuccess(openid.get());
        }else{
            return Response.builder().status(120).message("验证码错误").build();
        }

    }


}
