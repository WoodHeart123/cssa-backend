package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;
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
     * @param userID userID
     */
    void updateWechatID(String wechatID, String userID);


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
    List<CourseComment> getMyComment(@Param("userID")String userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void updateComment(String userID, Integer commentID, String comment);

    void deleteComment(String userID, Integer commentID);

    void updateNickname(String nickname, String userID);

    List<Product> getMySecondhand(@Param("userID")String userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void updateSecondHand(String userID, Product product);

    void deleteSecondHand(String userID, Integer productID);

    void setProductTime(Integer productID, String userID, Timestamp time);

    void clearProductTime(Integer productID, String userID);

    void setRentalTime(Integer rentalID, String userID, Timestamp time);

    void clearRentalTime(Integer rentalID, String userID);

    void deleteRental(String userID, Integer rentalID);

    ArrayList<Rental> getMyRental(String userID, Integer offset, Integer limit);

    List<MainPagePhoto> getMainPagePhotos();


}
