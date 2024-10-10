package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.cssa.wxcloudrun.config.CacheStore;
import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.model.CourseComment;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.ReturnCode;
import org.cssa.wxcloudrun.model.Subscription;
import org.cssa.wxcloudrun.service.RedisService;
import org.cssa.wxcloudrun.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "用户中心")
public class UserController {

    @Autowired
    CacheStore<Integer> authCodeCache;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;


    @RequestMapping(value = {"/getAuthCode"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取验证码", description = "获取验证码")
    public Response<Object> getAuthCode(@Parameter(description = "邮箱") @RequestParam String email,
                                        @RequestHeader("x-wx-openid") String openid) {
        Random ran = new Random();
        int authCode = ran.nextInt(1000000);
        redisService.set(String.format("authcode:%s", openid), authCode, 300);
        userService.getAuthCode(email, authCode);
        return Response.builder().status(100).build();
    }

    @RequestMapping(value = {"/verifyAuthCode"}, method = {RequestMethod.GET})
    @Operation(summary = "验证验证码", description = "验证验证码")
    public Response<Object> verifyAuthCod(@Parameter(description = "验证码") @RequestParam Integer authCode,
                                          @RequestHeader("x-wx-openid") String openid) {
        if (Objects.equals(redisService.get(String.format("authcode:%s", openid)), authCode)){
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
        return switch (service.toLowerCase()) {
            case "comment" -> new Response<>(userService.getMyComment(openid, offset, limit).getData());
            case "secondhand" -> new Response<>(userService.getMySecondhand(openid, offset, limit).getData());
            case "rental" -> new Response<>(userService.getMyRental(openid, offset, limit).getData());
            default -> new Response<>(ReturnCode.UNKNOWN_SERVICE);
        };

    }

    @RequestMapping(value = {"/updateComment"}, method = {RequestMethod.POST})
    @Operation(summary = "更新评论", description = "更新评论")
    public Response<Object> updateComment(@RequestBody CourseComment courseComment,
                                          @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (courseComment.getComment().length() > 300) {
            return new Response<>(ReturnCode.EXCEED_LENGTH_LIMIT);
        }
        return userService.updateComment(openid, courseComment.getCommentID(), courseComment.getComment());
    }

    @Deprecated
    @RequestMapping(value = {"/updateEmail"}, method = {RequestMethod.GET})
    @Operation(summary = "更新邮箱", description = "更新邮箱")
    public Response<Object> updateEmail(@RequestParam String email,
                                        @RequestParam Boolean subscribe,
                                        @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.updateEmail(email, subscribe, openid);
    }

    @Deprecated
    @RequestMapping(value = {"/updateAvatar"}, method = {RequestMethod.GET})
    @Operation(summary = "更新头像", description = "更新头像")
    public Response<Object> updateAvatar(@RequestParam Integer avatar,
                                         @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (avatar < 1 || avatar > 12) {
            return new Response<>(ReturnCode.INTEGER_OUT_OF_RANGE);
        }
        return userService.updateAvatar(openid, avatar);
    }

    @Deprecated
    @RequestMapping(value = {"/updateNickname"}, method = {RequestMethod.GET})
    @Operation(summary = "更新昵称", description = "更新昵称")
    public Response<Object> updateNickname(@RequestParam String nickname,
                                           @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (nickname.isEmpty()) {
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
                if (str.isEmpty()) {
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
    @Operation(summary = "更新二手", description = "更新二手")
    public Response<Object> updateSecondHand(@RequestBody Product product,
                                             @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.updateMySecondHand(openid, product);
    }

    @RequestMapping(value = {"/setTime"}, method = {RequestMethod.GET})
    @Operation(summary = "设置时间", description = "设置时间")
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
    @Operation(summary = "设置二手发布时间", description = "设置二手发布时间")
    public Response<Object> setProductTime(@RequestParam Integer productID,
                                           @RequestParam String UTCtime,
                                           @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String userID) {
        return userService.setProductTime(productID, userID, new Timestamp(Instant.parse(UTCtime).toEpochMilli()));
    }

    @Deprecated
    @RequestMapping(value = {"/deleteMySecondHand"}, method = {RequestMethod.POST})
    @Operation(summary = "删除二手", description = "删除二手")
    public Response<Object> deleteMySecondHand(@RequestParam Integer productID,
                                               @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return userService.deleteMySecondHand(openid, productID);
    }

    @Deprecated
    @RequestMapping(value = {"/deleteComment"}, method = {RequestMethod.POST})
    @Operation(summary = "删除评论", description = "删除评论")
    public Response<Object> deleteComment(@RequestBody Integer commentID,
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

    @RequestMapping(value = {"/unsubscribeByEncryptedID"}, method = {RequestMethod.POST})
    @Operation(summary = "退订邮件", description = "根据内部加密id退订邮件")
    public Response<Boolean> unsubscribeByEncryptedID(@RequestParam String encryptedID) {
        String openID = userMapper.getOpenIDFromEncryptedID(encryptedID);
        if (openID == null || openID.isBlank()) return new Response<>(ReturnCode.NO_SUCH_USER);
        return userService.unsubscribe(openID);
    }

    @RequestMapping(value = {"/isSubscribed"}, method = {RequestMethod.GET})
    @Operation(summary = "检查是否订阅", description = "检查是否订阅")
    public Response<Boolean> isSubscribed(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openID) {
        return userService.isSubscribed(openID);
    }

    @RequestMapping(value = {"isBlocked"}, method = {RequestMethod.GET})
    @Operation(summary = "检查用户是否被拉黑", description = "检查用户是否被拉黑")
    public Response<Boolean> isBlocked(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openID) {
        return userService.isBlocked(openID);
    }
}
