package org.cssa.wxcloudrun.dao;


import org.apache.ibatis.annotations.Mapper;
import org.cssa.wxcloudrun.model.FriendPost;

@Mapper
public interface FriendPostMapper {

    void createFriendPost(FriendPost friendPost);
}
