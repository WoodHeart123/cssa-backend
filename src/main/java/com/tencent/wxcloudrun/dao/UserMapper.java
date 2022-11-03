package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void authSuccess(String userID);

    /**
     * get user's info
     * @param userID user ID
     * @return email, nickname
     */
    User login(String userID);

    /**
     * user register
     * @param nickname wechat nickname
     * @param userID user ID
     */
    void register(String nickname, String userID);

    /**
     * update user email
     * @param email email
     * @param userID userID
     */
    void updateEmail(String email, String userID);

    /**
     * @param userID user ID given by wechat
     * @return a user object that only has likedComment field
     */
    User getLikedCommentList(String userID);

    /**
     * update user's avatar
     * @param avatar a number ranged from 1 to 12
     */
    void updateAvatar(Integer avatar);

    /**
     *
     * @param userID user ID
     * @param offset the starting commentID
     * @param limit number of comments
     * @return list of comment with limit size
     */
    List<Comment> getMyComment(@Param("userID")String userID, @Param("offset") Integer offset, @Param("limit") Integer limit);
    void updateComment(String userID, Integer commentID, String comment);

    void deleteComment(String userID, Integer commentID);
}
