package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
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
     * @param nickname WeChat nickname
     * @param userID user ID
     */
    void register(String nickname, String userID);

    /**
     * update user email
     * @param email email
     * @param userID userID
     */
    void updateEmail(String email, Boolean subscribe, String userID);

    /**
     * update user wechatID
     * @param email email
     * @param userID userID
     */
    void updateWechatID(String wechatID, String userID);

    /**
     * @param userID user ID given by WeChat
     * @return a user object that only has likedComment field
     */
    User getLikedCommentList(String userID);

    /**
     * update user's avatar
     * @param avatar a number ranged from 1 to 12
     */
    void updateAvatar(String userID,Integer avatar);

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

    void updateNickname(String nickname, String userID);

    List<Product> getMySecondhand(@Param("userID")String userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void updateMySecondHand(String userID, Product product);

    void deleteMySecondHand(String userID, Integer productID);

    void setProductTime(Integer productID, String userID, Timestamp time);

    /**
     * 删除收藏
     * @param collect 收藏信息
     */
    void deleteCollect(Collect collect);

    /**
     * 添加收藏
     * @param collect 收藏信息
     */
    void addCollect(Collect collect);

    /**
     *  获取用户收藏内容
     * @param collectType 收藏类型
     * @param userID 用户ID
     */
    List<Integer> getCollectID(@Param("collectType")CollectType collectType,@Param("userID")String userID);

    List<Product> getProductCollectList(CollectType collectType,String userID,Integer offset, Integer limit);
    List<Rental> getRentalCollectList(CollectType collectType,String userID,Integer offset, Integer limit);



}
