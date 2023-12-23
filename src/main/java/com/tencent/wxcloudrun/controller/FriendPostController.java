package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.FriendComment;
import com.tencent.wxcloudrun.model.FriendPost;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.FriendPostService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/friendpost"})
@Api(tags = "麦屯圈中心")
public class FriendPostController {

    @Autowired
    private FriendPostService friendPostService;

    @RequestMapping(value = {"/createFriendPost"}, method = {RequestMethod.POST}, produces = "application/json")
    @Operation(summary = "发表麦屯圈", description = "发表麦屯圈")
    public Response<Object> createFriendPost(@Parameter(description = "帖子") @RequestBody FriendPost friendPost,
                                             @Parameter(description = "wx open id") @RequestHeader("x-wx-openid") String openid) {
        friendPost.setUserID(openid);
        return friendPostService.createFriendPost(friendPost);
    }

    @RequestMapping(value = {"/getFriendPost"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取麦屯圈列表", description = "获取麦屯圈列表")
    public Response<List<FriendPost>> getFriendPost(@Parameter(description = "从第几位开始") @RequestParam Integer offset,
                                                    @Parameter(description = "返回数量限制") @RequestParam Integer limit) {
        return null;
    }


    @RequestMapping(value = {"/getFriendComment"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取麦屯圈评论列表", description = "获取麦屯圈评论列表")
    public Response<List<FriendComment>> getFriendComment(@Parameter(description = "从第几位开始") @RequestParam Integer offset,
                                                          @Parameter(description = "返回数量限制") @RequestParam Integer limit,
                                                          @Parameter(description = "帖子ID") @RequestParam() Integer postID) {
        return null;
    }
}
