package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
    void get_zan(String openid, Integer commentID, short zan);

    User get_user(String openid);

    void create_zan(String openid, Integer commentID, short zan);

    Comment get_comment(Integer commentID);

    void create_post(Integer commentID, String report);

    void get_post(Integer commentID, String report);

    void disable_comment(Integer commentID);
}
