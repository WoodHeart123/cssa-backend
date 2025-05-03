package org.cssa.wxcloudrun.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.model.*;
import org.cssa.wxcloudrun.service.MessageService;
import org.cssa.wxcloudrun.service.WeChatAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping({"/message"})
@Tag(name = "留言中心")
public class MessageController {

    @Autowired
    WeChatAPI weChatAPI;

    @Autowired
    MessageService messageService;


    @PostMapping(value = "/post")
    @Operation(summary = "发表留言", description = "发表留言")
    public Response<Object> createMessage(@Parameter(description = "课程评价") @RequestBody Message message,
                                 @Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        if (message.getContent().length() > 100) {
            return Response.builder().status(103).message("超过字数限制").build();
        }

        WechatResponse wechatResponse = weChatAPI.MsgCheck(message.getContent(), openid, 3);
        if(wechatResponse.getResult().getLabel() >= 20000){
            return new Response<>(ReturnCode.CENSORED_UGC_CONTENT);
        }
        message.setReplierUserID(openid);
        return messageService.createMessage(message);

    }

    @PostMapping(value = "/get/list")
    @Operation(summary = "发表留言", description = "发表留言")
    public Response<Object> getCommentListUnderPost(@Parameter(description = "帖子ID") @RequestParam Integer postID,
                                                    @Parameter(description = "帖子类型") @RequestParam Message.PostType postType) {

        return messageService.getMessageListUnderPost(postID, postType);

    }

    @PostMapping(value = "/get/new")
    @Operation(summary = "发表留言", description = "获取未读留言")
    public Response<Object> getNewMessageList(@Parameter(description = "微信ID") @RequestHeader("x-wx-openid") String openid) {
        return messageService.getNewMessageList(openid);

    }



}
