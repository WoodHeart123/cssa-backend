package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.FriendComment;
import com.tencent.wxcloudrun.model.FriendPost;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.service.FriendPostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendPostServiceImpl implements FriendPostService {
    @Override
    public Response<Object> createFriendPost(FriendPost friendPost) {
        return null;
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
