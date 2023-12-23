package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.FriendComment;
import com.tencent.wxcloudrun.model.FriendPost;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendPostService {

    public Response<Object> createFriendPost(FriendPost friendPost);

    public Response<List<FriendPost>> getFriendPostList(Integer offset, Integer limit);

    public Response<List<FriendComment>> getFriendCommentList(Integer offset, Integer limit, Integer postID);

}
