package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cssa.wxcloudrun.dao.FriendPostMapper;
import org.cssa.wxcloudrun.model.FriendComment;
import org.cssa.wxcloudrun.model.FriendPost;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.FriendPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendPostServiceImpl implements FriendPostService {

    @Autowired
    public FriendPostMapper friendPostMapper;


    @Override
    public Response<Object> createFriendPost(FriendPost friendPost) {
        friendPost.setImagesJSON(JSON.toJSONString(friendPost.getImages()));
        friendPostMapper.createFriendPost(friendPost);
        return new Response<>();
    }

    @Override
    public Response<Object> createFriendComment(FriendComment friendComment) {
        friendPostMapper.createFriendComment(friendComment);
        return new Response<>();
    }

    @Override
    public Response<List<FriendPost>> getFriendPostList(Integer offset, Integer limit) {
        List<FriendPost> friendPostList = friendPostMapper.getFriendPostList(offset, limit);
        for (FriendPost friendPost : friendPostList) {
            friendPost.setImages((ArrayList<String>) JSON.parseArray(friendPost.getImagesJSON(), String.class));
        }
        return new Response<>(friendPostList);
    }

    @Override
    public Response<List<FriendComment>> getFriendCommentList(Integer offset, Integer limit, Integer postID) {
        return new Response<>(friendPostMapper.getFriendCommentList(offset,limit, postID));
    }
}
