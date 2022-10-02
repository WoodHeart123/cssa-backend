package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;

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

}
