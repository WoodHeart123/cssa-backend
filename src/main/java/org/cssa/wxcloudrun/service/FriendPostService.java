package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.FriendComment;
import org.cssa.wxcloudrun.model.FriendPost;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendPostService {

    Response<Object> createFriendPost(FriendPost friendPost);

    Response<List<FriendPost>> getFriendPostList(Integer offset, Integer limit);

    Response<List<FriendComment>> getFriendCommentList(Integer offset, Integer limit, Integer postID);

}
