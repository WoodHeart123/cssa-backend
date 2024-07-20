package org.cssa.wxcloudrun.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cssa.wxcloudrun.model.CourseComment;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.model.Rental;
import org.cssa.wxcloudrun.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    void authSuccess(String userID);

    /**
     * get user's info
     *
     * @param userID user ID
     * @return email, nickname
     */
    User login(String userID);

    /**
     * user register
     *
     * @param nickname WeChat nickname
     * @param userID   user ID
     */
    void register(String nickname, String userID);

    /**
     * 更新数据库中用户的邮箱和订阅状态。
     *
     * 该方法执行一个 SQL 更新语句，设置用户表中由 'userID' 标识的用户的 'email' 和 'subscribe' 字段。
     *
     * @param email     要设置的用户新邮箱地址。此参数对应用户表中的 'email' 字段。
     * @param subscribe 要设置的用户新的订阅状态。此参数对应用户表中的 'subscribe' 字段。
     * @param userID    要更新邮箱和订阅状态的用户的唯一标识符。此参数对应用户表中的 'userID' 字段。
     */
    void updateEmail(String email, Boolean subscribe, String userID);

    /**
     * update user wechatID
     *
     * @param userID userID
     */
    void updateWechatID(String wechatID, String userID);


    /**
     * update user's avatar
     *
     * @param avatar a number ranged from 1 to 12
     */
    void updateAvatar(String userID, Integer avatar);

    /**
     * @param userID user ID
     * @param offset the starting commentID
     * @param limit  number of comments
     * @return list of comment with limit size
     */
    List<CourseComment> getMyComment(@Param("userID") String userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void updateComment(String userID, Integer commentID, String comment);

    void deleteComment(String userID, Integer commentID);

    void updateNickname(String nickname, String userID);

    List<Product> getMySecondhand(@Param("userID") String userID, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void updateSecondHand(String userID, Product product);

    void deleteSecondHand(String userID, Integer productID);

    void setProductTime(Integer productID, String userID, Timestamp time);

    void clearProductTime(Integer productID, String userID);

    void setRentalTime(Integer rentalID, String userID, Timestamp time);

    void clearRentalTime(Integer rentalID, String userID);

    void deleteRental(String userID, Integer rentalID);

    ArrayList<Rental> getMyRental(String userID, Integer offset, Integer limit);

    /**
     * 获取数据库中指定用户的邮箱地址。
     *
     * 该方法执行一个 SQL 查询语句，根据用户的唯一标识符 'userID' 从用户表中获取对应的邮箱地址。
     *
     * @param userID 要获取邮箱地址的用户的唯一标识符。此参数对应用户表中的 'userID' 字段。
     * @return 返回对应用户的邮箱地址。如果用户不存在，则返回 null。
     */
    String getEmail(String userID);

    Boolean isSubscribed(String openID);
}
