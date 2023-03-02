package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Instant;
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
    public Response getAuthCode(@RequestParam String email, @RequestHeader("x-wx-openid") String openid){
        Random ran = new Random();
        int authCode = ran.nextInt(1000000);
        authCodeCache.add(openid,authCode);
        userService.getAuthCode(email,authCode);
        return Response.builder().status(100).build();
    }

    @RequestMapping(value={"/verifyAuthCode"}, method = {RequestMethod.GET})
    public Response verifyAuthCod(@RequestParam Integer authCode, @RequestHeader("x-wx-openid") String openid){
        if(authCodeCache.get(openid) != null && Objects.equals(authCodeCache.get(openid), authCode)){
            return userService.authSuccess(openid);
        }else{
            return Response.builder().status(106).message("验证码错误").build();
        }

    }
    @RequestMapping(value={"/login"}, method={RequestMethod.GET})
    public Response login(@RequestParam String nickname, @RequestHeader("x-wx-openid") String openid) throws UnsupportedEncodingException {
        return userService.login(nickname, openid);
    }

    @RequestMapping(value={"/updateEmail"}, method={RequestMethod.GET})
    public Response updateEmail(@RequestParam String email,@RequestParam Boolean subscribe, @RequestHeader("x-wx-openid") String openid){
        return userService.updateEmail(email, subscribe, openid);
    }

    @RequestMapping(value={"/updateAvatar"}, method={RequestMethod.GET})
    public Response updateAvatar(@RequestParam Integer avatar, @RequestHeader("x-wx-openid") String openid){
        if(avatar < 1 || avatar > 12){
            return new Response(ReturnCode.INTEGER_OUT_OF_RANGE);
        }
        return userService.updateAvatar(openid,avatar);
    }

    @RequestMapping(value={"/getZanList"}, method={RequestMethod.GET})
    public Response getZanList(@RequestHeader("x-wx-openid") String openid){
        return userService.getLikedCommentList(openid);
    }

    @RequestMapping(value={"/getMyComment"}, method={RequestMethod.GET})
    public Response getMyComment(@RequestParam Integer offset, @RequestParam Integer limit,@RequestHeader("x-wx-openid") String openid){
        return userService.getMyComment(openid,offset,limit);
    }

    @RequestMapping(value={"/getMySecondhand"}, method={RequestMethod.GET})
    public Response getMySecondhand(@RequestParam Integer offset, @RequestParam Integer limit,@RequestHeader("x-wx-openid") String openid){
        return userService.getMySecondhand(openid,offset,limit);
    }

    @RequestMapping(value={"/updateComment"},method={RequestMethod.POST})
    public Response updateComment(@RequestBody Comment comment, @RequestHeader("x-wx-openid") String openid){
        if(comment.getComment().length() > 300){
            return new Response(ReturnCode.EXCEED_LENGTH_LIMIT);
        }
        return userService.updateComment(openid,comment.getCommentID(),comment.getComment());
    }
    @RequestMapping(value={"/deleteComment"},method={RequestMethod.POST})
    public Response deleteComment(@RequestBody Integer commentID, @RequestHeader("x-wx-openid") String openid){
        return userService.deleteComment(openid, commentID);
    }

    @RequestMapping(value={"/updateNickname"},method={RequestMethod.GET})
    public Response updateNickname(@RequestParam String nickname,@RequestHeader("x-wx-openid") String openid){
        if(nickname.length() == 0) {
            return new Response(ReturnCode.EMPTY_STRING);
        }
        return userService.updateNickname(openid,nickname);
    }

    @RequestMapping(value={"/updateWechatID"}, method={RequestMethod.GET})
    public Response updateWechatID(@RequestParam String wechatID, @RequestHeader("x-wx-openid") String openid){
        return userService.updateWechatID(wechatID, openid);
    }

    @RequestMapping(value={"/updateSecondHand"},method={RequestMethod.POST})
    public Response updateMySecondHand(@RequestBody Product product, @RequestHeader("x-wx-openid") String openid){
        return userService.updateMySecondHand(openid, product);
    }

    @RequestMapping(value={"/setProductTime"},method={RequestMethod.GET})
    public Response setProductTime(@RequestParam Integer productID,@RequestParam String UTCtime, @RequestHeader("x-wx-openid") String userID){
        return userService.setProductTime(productID, userID, new Timestamp(Instant.parse(UTCtime).toEpochMilli()));
    }
    @RequestMapping(value={"/deleteMySecondHand"},method={RequestMethod.POST})
    public Response deleteMySecondHand(@RequestParam Integer productID, @RequestHeader("x-wx-openid") String openid){
        return userService.deleteMySecondHand(openid, productID);
    }

    /**
     * 收藏或取消收藏
     * @param userID user ID
     * @param save true 为收藏, false 为取消
     */
    @RequestMapping(value= {"/collect"}, method = {RequestMethod.POST})
    public Response collect(@RequestBody Collect collect, @RequestHeader("x-wx-openid") String userID, @RequestParam Boolean save){
        collect.setUserID(userID);
        return userService.collect(collect, save);
    }

    @RequestMapping(value = {"/getCollectID"}, method = {RequestMethod.GET})
    public Response getCollectID(@RequestParam CollectType collectType, @RequestHeader("x-wx-openid") String userID){
        return userService.getCollectID(collectType, userID);
    }

    @RequestMapping(value = {"/getCollectList"}, method = {RequestMethod.GET})
    public Response getCollectList(@RequestParam CollectType collectType,@RequestParam Integer offset, @RequestParam Integer limit, @RequestHeader("x-wx-openid") String userID){
        return userService.getCollectList(collectType, userID, offset, limit);
    }
}
