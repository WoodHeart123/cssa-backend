package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.CacheStore;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping({"/user"})
@Api(tags = "用户中心")
public class UserController {

    @Autowired
    CacheStore<Integer> authCodeCache;

    @Autowired
    UserService userService;


    @RequestMapping(value = {"/getAuthCode"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取验证码", description = "获取验证码")
    public Response<Object> getAuthCode(@Parameter(description = "邮箱") @RequestParam String email,
                                        @RequestHeader("x-wx-openid") String openid) {
        Random ran = new Random();
        int authCode = ran.nextInt(1000000);
        authCodeCache.add(openid, authCode);
        userService.getAuthCode(email, authCode);
        return Response.builder().status(100).build();
    }

    @RequestMapping(value = {"/verifyAuthCode"}, method = {RequestMethod.GET})
    @Operation(summary = "验证验证码", description = "验证验证码")
    public Response<Object> verifyAuthCod(@Parameter(description = "验证码") @RequestParam Integer authCode,
                                          @RequestHeader("x-wx-openid") String openid) {
        if (authCodeCache.get(openid) != null && Objects.equals(authCodeCache.get(openid), authCode)) {
            return userService.authSuccess(openid);
        } else {
            return Response.builder().status(106).message("验证码错误").build();
        }

    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "登录", description = "登录")
    public Response<Object> login(@Parameter(description = "昵称，第一次登录需要") @RequestParam String nickname,
                                  @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) throws UnsupportedEncodingException {
        return userService.login(nickname, openid);
    }

    @RequestMapping(value = {"/getMyComment"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取我的评论", description = "获取我的评论")
    public Response<List<CourseComment>> getMyComment(@RequestParam Integer offset,
                                                      @RequestParam Integer limit,
                                                      @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.getMyComment(openid, offset, limit);
    }

    @RequestMapping(value = {"/getMySecondhand"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取我的二手", description = "获取我的二手")
    public Response<List<Product>> getMySecondhand(@RequestParam Integer offset,
                                                   @RequestParam Integer limit,
                                                   @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.getMySecondhand(openid, offset, limit);
    }
    @Deprecated
    @RequestMapping(value = {"/getMyList"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取我的列表", description = "获取我的列表，可选项为comment, secondhand, rental")
    public Response<Object> getMyList(@RequestParam String service,
                                      @RequestParam Integer offset,
                                      @RequestParam Integer limit,
                                      @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        switch (service.toLowerCase()) {
            case "comment":
                return new Response<>(userService.getMyComment(openid, offset, limit).getData());
            case "secondhand":
                return new Response<>(userService.getMySecondhand(openid, offset, limit).getData());
            case "rental":
                return new Response<>(userService.getMyRental(openid, offset, limit).getData());
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }

    }

    @RequestMapping(value = {"/updateComment"}, method = {RequestMethod.POST})
    public Response<Object> updateComment(@RequestBody CourseComment courseComment,
                                          @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (courseComment.getComment().length() > 300) {
            return new Response<>(ReturnCode.EXCEED_LENGTH_LIMIT);
        }
        return userService.updateComment(openid, courseComment.getCommentID(), courseComment.getComment());
    }

    @Deprecated
    @RequestMapping(value = {"/updateEmail"}, method = {RequestMethod.GET})
    public Response<Object> updateEmail(@RequestParam String email,
                                        @RequestParam Boolean subscribe,
                                        @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.updateEmail(email, subscribe, openid);
    }

    @Deprecated
    @RequestMapping(value = {"/updateAvatar"}, method = {RequestMethod.GET})
    public Response<Object> updateAvatar(@RequestParam Integer avatar,
                                         @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (avatar < 1 || avatar > 12) {
            return new Response<>(ReturnCode.INTEGER_OUT_OF_RANGE);
        }
        return userService.updateAvatar(openid, avatar);
    }

    @Deprecated
    @RequestMapping(value = {"/updateNickname"}, method = {RequestMethod.GET})
    public Response<Object> updateNickname(@RequestParam String nickname,
                                           @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (nickname.length() == 0) {
            return new Response<>(ReturnCode.EMPTY_STRING);
        }
        return userService.updateNickname(openid, nickname);
    }

    @RequestMapping(value = {"/updateProfile"}, method = {RequestMethod.GET})
    public Response<Object> updateProfile(@RequestParam String service,
                                          @RequestParam String str,
                                          @RequestParam Optional<Boolean> subscribe,
                                          @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        switch (service.toLowerCase()) {
            case "wechatid":
                return userService.updateWechatID(str, openid);
            case "nickname":
                if (str.length() == 0) {
                    return new Response<>(ReturnCode.EMPTY_STRING);
                }
                return userService.updateNickname(openid, str);
            case "avatar":
                try {
                    int avatar = Integer.parseInt(str);
                    if (avatar < 1 || avatar > 12) {
                        return new Response<>(ReturnCode.INTEGER_OUT_OF_RANGE);
                    }
                    return userService.updateAvatar(openid, avatar);
                } catch (NumberFormatException e) {
                    return new Response<>(ReturnCode.INVALID_TYPE);
                }
            case "email":
                if (subscribe.isEmpty()) {
                    return new Response<>(ReturnCode.LACK_PARAM);
                }
                return userService.updateEmail(str, subscribe.get(), openid);
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }
    }

    @Deprecated
    @RequestMapping(value = {"/updateSecondHand"}, method = {RequestMethod.POST})
    public Response<Object> updateSecondHand(@RequestBody Product product,
                                             @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.updateMySecondHand(openid, product);
    }

    @RequestMapping(value = {"/setTime"}, method = {RequestMethod.GET})
    public Response<Object> setTime(@RequestParam String service,
                                    @RequestParam Integer itemID,
                                    @RequestParam String UTCtime,
                                    @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String userID) {
        Timestamp time = new Timestamp(Instant.parse(UTCtime).toEpochMilli());
        switch (service.toLowerCase()) {
            case "product":
                return userService.setProductTime(itemID, userID, time);
            case "rental":
                return userService.setRentalTime(itemID, userID, time);
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }
    }

    @Deprecated
    @RequestMapping(value = {"/setProductTime"}, method = {RequestMethod.GET})
    public Response setProductTime(@RequestParam Integer productID,
                                   @RequestParam String UTCtime,
                                   @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String userID) {
        return userService.setProductTime(productID, userID, new Timestamp(Instant.parse(UTCtime).toEpochMilli()));
    }

    @Deprecated
    @RequestMapping(value = {"/deleteMySecondHand"}, method = {RequestMethod.POST})
    public Response deleteMySecondHand(@RequestParam Integer productID,
                                       @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.deleteMySecondHand(openid, productID);
    }

    @Deprecated
    @RequestMapping(value = {"/deleteComment"}, method = {RequestMethod.POST})
    public Response deleteComment(@RequestBody Integer commentID,
                                  @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.deleteComment(openid, commentID);
    }

    @RequestMapping(value = {"deleteMyItem"}, method = RequestMethod.DELETE)
    public Response<Object> deleteMyItem(@RequestParam String service,
                                         @RequestParam Integer itemID,
                                         @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        switch (service.toLowerCase()) {
            case "comment":
                return userService.deleteComment(openid, itemID);
            case "secondhand":
                return userService.deleteMySecondHand(openid, itemID);
            case "rental":
                return userService.deleteMyRental(openid, itemID);
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }
    }

    /**
     * @return list of main page photo to mini-program
     */
    @RequestMapping(value = {"getMainPagePhotos"}, method = {RequestMethod.GET})
    Response<List<MainPagePhoto>> getMainPagePhotos() {
        return userService.getMainPagePhotos();
    }
}
