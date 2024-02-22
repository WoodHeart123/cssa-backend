package org.cssa.wxcloudrun.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.cssa.wxcloudrun.model.FriendComment;
import org.cssa.wxcloudrun.model.FriendPost;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.FriendPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/friendpost"})
@Tag(name = "麦屯圈中心")
public class FriendPostController {

    @Autowired
    private FriendPostService friendPostService;

    @RequestMapping(value = {"/create"}, method = {RequestMethod.POST}, produces = "application/json")
    @Operation(summary = "发表麦屯圈", description = "发表麦屯圈")
    public Response<Object> createFriendPost(@Parameter(description = "帖子") @RequestBody FriendPost friendPost,
                                             @Parameter(description = "wx open id") @RequestHeader("x-wx-openid") String openid) {
        friendPost.setUserID(openid);
        return friendPostService.createFriendPost(friendPost);
    }

    @RequestMapping(value = {"/comment/create"}, method = {RequestMethod.POST}, produces = "application/json")
    @Operation(summary = "发表评论", description = "发表评论")
    public Response<Object> createFriendComment(@Parameter(description = "帖子评论") @RequestBody FriendComment friendComment,
                                             @Parameter(description = "wx open id") @RequestHeader("x-wx-openid") String openid) {
        friendComment.setUserID(openid);
        return friendPostService.createFriendComment(friendComment);
    }

    @RequestMapping(value = {"/get"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取麦屯圈列表", description = "获取麦屯圈列表")
    public Response<List<FriendPost>> getFriendPostList(@Parameter(description = "从第几位开始") @RequestParam Integer offset,
                                                    @Parameter(description = "返回数量限制") @RequestParam Integer limit) {
        return friendPostService.getFriendPostList(offset, limit);
    }


    @RequestMapping(value = {"/getFriendComment"}, method = {RequestMethod.GET}, produces = "application/json")
    @Operation(summary = "获取麦屯圈评论列表", description = "获取麦屯圈评论列表")
    public Response<List<FriendComment>> getFriendComment(@Parameter(description = "从第几位开始") @RequestParam Integer offset,
                                                          @Parameter(description = "返回数量限制") @RequestParam Integer limit,
                                                          @Parameter(description = "帖子ID") @RequestParam() Integer postID) {
        return friendPostService.getFriendCommentList(offset, limit, postID);
    }
}
