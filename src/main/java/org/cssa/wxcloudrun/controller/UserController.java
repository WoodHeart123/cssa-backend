package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.cssa.wxcloudrun.config.CacheStore;
import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.model.Subscription;
import org.cssa.wxcloudrun.service.CourseService;
import org.cssa.wxcloudrun.service.RedisService;
import org.cssa.wxcloudrun.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.util.InjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping({"/user"})
@Tag(name = "用户中心")
public class UserController {

    @Autowired
    CacheStore<Integer> authCodeCache;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    CourseService courseService;

    @Autowired
    UserMapper userMapper;


    @RequestMapping(value = {"/getAuthCode"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取验证码", description = "获取验证码")
    public Response<Object> getAuthCode(@Parameter(description = "邮箱") @RequestParam String email,
                                        @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        Random ran = new Random();
        int authCode = ran.nextInt(1000000);
        redisService.set(String.format("authcode:%s", user.getId()), authCode, 300);
        userService.getAuthCode(email, authCode);
        return Response.builder().status(100).build();
    }

    @RequestMapping(value = {"/verifyAuthCode"}, method = {RequestMethod.GET})
    @Operation(summary = "验证验证码", description = "验证验证码")
    public Response<Object> verifyAuthCod(@Parameter(description = "验证码") @RequestParam Integer authCode,
                                          @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        if (Objects.equals(redisService.get(String.format("authcode:%s", user.getId())), authCode)){
            User userChanged = User.builder().id(user.getId()).isStudent(true).build();
            userService.updateUser(userChanged);
            return new Response<>();
        } else {
            return Response.builder().status(106).message("验证码错误").build();
        }
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "微信登录", description = "微信登录")
    public Response<Object> login(@Parameter(description = "昵称，第一次登录需要") @RequestParam String nickname,
                                  @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) throws UnsupportedEncodingException {
        return userService.wechatLogin(nickname, openid);
    }


    @RequestMapping(value = {"/updateProfile"}, method = {RequestMethod.GET})
    public Response<Object> updateProfile(@RequestParam String service,
                                          @RequestParam String str,
                                          @RequestParam Optional<Boolean> subscribe,
                                          @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        User userChanged;
        switch (service.toLowerCase()) {
            case "wechatid":
                userChanged = User.builder().wechatID(str).id(user.getId()).build();
                return userService.updateUser(userChanged);
            case "nickname":
                if (str.isEmpty()) {
                    return new Response<>(ReturnCode.EMPTY_STRING);
                }
                userChanged = User.builder().nickname(str).id(user.getId()).build();
                return userService.updateUser(userChanged);
            case "avatar":
                try {
                    int avatar = Integer.parseInt(str);
                    if (avatar < 1 || avatar > 12) {
                        return new Response<>(ReturnCode.INTEGER_OUT_OF_RANGE);
                    }
                    userChanged = User.builder().avatar(avatar).id(user.getId()).build();
                    return userService.updateUser(userChanged);
                } catch (NumberFormatException e) {
                    return new Response<>(ReturnCode.INVALID_TYPE);
                }
            case "email":
                if (subscribe.isEmpty()) {
                    return new Response<>(ReturnCode.LACK_PARAM);
                }
                Pattern pattern = Pattern.compile("^([-a-zA-Z0-9_.]+)@([-a-zA-Z0-9_.]+).([a-zA-Z]{2,5})$");
                Matcher matcher = pattern.matcher(str);
                if (!matcher.matches()) {
                    return new Response<>(ReturnCode.INVALID_FORMAT);
                }
                userChanged = User.builder().email(str).subscribe(subscribe.get()).id(user.getId()).build();
                return userService.updateUser(userChanged);
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }
    }

    @RequestMapping(value = {"/setTime"}, method = {RequestMethod.GET})
    @Operation(summary = "设置时间", description = "设置时间")
    public Response<Object> setTime(@RequestParam String service,
                                    @RequestParam Integer itemID,
                                    @RequestParam String UTCtime,
                                    @Parameter(description = "用户信息", hidden = true) @InjectUser User user) {
        Timestamp time = new Timestamp(Instant.parse(UTCtime).toEpochMilli());
        switch (service.toLowerCase()) {
            case "product":
                return userService.setProductTime(itemID, user.getId(), time);
            case "rental":
                return userService.setRentalTime(itemID, user.getId(), time);
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }
    }
    @RequestMapping(value = {"deleteMyItem"}, method = RequestMethod.DELETE)
    public Response<Object> deleteMyItem(@RequestParam String service,
                                         @RequestParam Integer itemID,
                                         @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        switch (service.toLowerCase()) {
            case "comment":
                //return userService.deleteComment(openid, itemID);
            case "secondhand":
                //return userService.deleteMySecondHand(openid, itemID);
            case "rental":
                //return userService.deleteMyRental(openid, itemID);
            default:
                return new Response<>(ReturnCode.UNKNOWN_SERVICE);
        }
    }

    @RequestMapping(value = {"/subscribe"}, method = {RequestMethod.POST})
    @Operation(summary = "订阅邮件", description = "订阅邮件")
    public Response<Boolean> subscribe(@RequestBody Subscription subscription) {
        return userService.subscribe(subscription);
    }

    @RequestMapping(value = {"/unsubscribeByOpenID"}, method = {RequestMethod.POST})
    @Operation(summary = "退订邮件", description = "根据微信open id退订邮件")
    public Response<Boolean> unsubscribeByOpenID(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openID) {
        return userService.unsubscribe(openID);
    }

//    @RequestMapping(value = {"/unsubscribeByEncryptedID"}, method = {RequestMethod.POST})
//    @Operation(summary = "退订邮件", description = "根据内部加密id退订邮件")
//    public Response<Boolean> unsubscribeByEncryptedID(@RequestParam String encryptedID) {
//        String openID = userMapper.getOpenIDFromEncryptedID(encryptedID);
//        if (openID == null || openID.isBlank()) return new Response<>(ReturnCode.NO_SUCH_USER);
//        return userService.unsubscribe(openID);
//    }

    @RequestMapping(value = {"/isSubscribed"}, method = {RequestMethod.GET})
    @Operation(summary = "检查是否订阅", description = "检查是否订阅")
    public Response<Boolean> isSubscribed(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openID) {
        return userService.isSubscribed(openID);
    }

    @RequestMapping(value = {"/saveUserInfo"}, method = {RequestMethod.POST})
    @Operation(summary = "保存真实的用户信息", description = "保存用户目前真实的昵称和头像链接。需要前端判断是否需要更新。")
    public Response<Boolean> saveUserInfo(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openId,
                                          @RequestParam(required = false) String nickname,
                                          @RequestParam(required = false) String avatarUrl) {
        return userService.saveUserInfo(openId, nickname, avatarUrl);
    }

    @RequestMapping(value = {"/getUserInfo"}, method = {RequestMethod.GET})
    @Operation(summary = "获取真实的用户信息",description = "获取数据库中用户的头像链接，昵称，是否为学生，是否被拉黑")
    public Response<Object> getUserInfo(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openId) {
        return userService.getUserInfo(openId);
    }
}
