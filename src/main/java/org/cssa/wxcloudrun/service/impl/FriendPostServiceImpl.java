package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import org.cssa.wxcloudrun.dao.FriendPostMapper;
import org.cssa.wxcloudrun.model.FriendComment;
import org.cssa.wxcloudrun.model.FriendPost;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.FriendPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Response<List<FriendPost>> getFriendPostList(Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Response<List<FriendComment>> getFriendCommentList(Integer offset, Integer limit, Integer postID) {
        return null;
    }
}
