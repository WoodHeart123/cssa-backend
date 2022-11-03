package com.tencent.wxcloudrun.controller;


import com.github.twohou.sonic.ChannelFactory;
import com.tencent.wxcloudrun.model.CacheStore;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    private ChannelFactory channelFactory;


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
            return Response.builder().status(106).message("验证码错误").build();
        }

    }
    @RequestMapping(value={"/login"}, method={RequestMethod.GET})
    public Response login(@RequestParam String nickname, HttpServletRequest request) throws UnsupportedEncodingException {
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return userService.login(nickname, openid.get());
    }

    @RequestMapping(value={"/updateEmail"}, method={RequestMethod.GET})
    public Response updateEmail(String email, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return userService.updateEmail(email,openid.get());
    }

    @RequestMapping(value={"/updateAvatar"}, method={RequestMethod.GET})
    public Response updateAvatar(Integer avatar, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        if(avatar < 1 || avatar > 12){
            return Response.builder().status(110).build();
        }
        return userService.updateAvatar(avatar);
    }

    @RequestMapping(value={"/getZanList"}, method={RequestMethod.GET})
    public Response getZanList(HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return userService.getLikedCommentList(openid.get());
    }

    @RequestMapping(value={"/getMyComment"}, method={RequestMethod.GET})
    public Response getMyComment(@RequestParam Integer offset, @RequestParam Integer limit,HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return userService.getMyComment(openid.get(),offset,limit);
    }

    @RequestMapping(value={"/updateComment"},method={RequestMethod.POST})
    public Response updateComment(@RequestBody Comment comment, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        if(comment.getComment().length() > 300){
            return Response.builder().status(103).message("超过字数限制").build();
        }
        return userService.updateComment(openid.get(),comment.getCommentID(),comment.getComment());
    }
    @RequestMapping(value={"/deleteComment"},method={RequestMethod.POST})
    public Response updateComment(@RequestBody Integer commentID, HttpServletRequest request){
        Optional<String> openid = Optional.ofNullable(request.getHeader("x-wx-openid"));
        if(openid.isEmpty()){
            return Response.builder().status(102).message("无用户信息").build();
        }
        return userService.deleteComment(openid.get(), commentID);
    }
}
