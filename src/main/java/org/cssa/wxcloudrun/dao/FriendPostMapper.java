package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.FriendComment;
import org.cssa.wxcloudrun.model.FriendPost;

import java.util.List;

@Mapper
public interface FriendPostMapper {

    void createFriendComment(FriendComment friendComment);

    void createFriendPost(FriendPost friendPost);

    List<FriendPost> getFriendPostList(Integer offset, Integer limit);

    List<FriendComment> getFriendCommentList(Integer offset, Integer limit, Integer postID);
}
