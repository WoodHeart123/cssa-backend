package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.FriendPost;

import java.util.List;

@Mapper
public interface FriendPostMapper {

    void createFriendPost(FriendPost friendPost);

    List<FriendPost> getFriendPostList(Integer offset, Integer limit);
}
