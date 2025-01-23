package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cssa.wxcloudrun.model.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * get user's info by openID
     *
     * @param openID user ID
     * @return email, nickname
     */
    User getUserByOpenID(String openID);

    /**
     * create user
     *
     * @param nickname nickname
     */
    void createUser(User user);

    /**
     * get user's info by userID
     *
     * @param userID user ID
     * @return user
     */
    User getUserByID(Integer userID);

    /**
     * update user's info
     *
     * @param user user
     */
    int updateUser(User user);

    void setProductTime(Integer productID, Integer userID, Timestamp time);

    void clearProductTime(Integer productID, Integer userID);

    void setRentalTime(Integer rentalID, Integer userID, Timestamp time);

    void clearRentalTime(Integer rentalID, Integer userID);


    Boolean isSubscribed(String openID);


    Boolean ifEncryptedIDExists(String encryptedID);

    void saveContact(@Param("userId") String userId, @Param("contactInfo") Contact contactInfo);

    boolean saveUserInfo(@Param("userId") String userId,@Param("nickName") String nickName,@Param("avatarUrl") String avatarUrl);

    Map<String, String> getUserInfo(String openId);

}
