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
     * @param subscribe 要设置的用户新订阅状态。此参数对应用户表中的 'subscribe' 字段。
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

    /**
     * 检查指定用户是否已订阅。
     * 此方法查询user表，根据用户的微信openID检查subscribe字段，
     * 以确定该用户是否已经订阅服务。
     *
     * @param openID 用户的微信openID，用于定位用户记录。
     * @return Boolean 返回true如果用户已订阅，否则返回false。
     */
    Boolean isSubscribed(String openID);

    /**
     * 使用提供的加密ID检索用户的Open ID。
     * 该方法通过解码或查找加密ID来找到相应的Open ID，
     * 通常用于在ID加密提供额外安全性的系统中识别用户。
     *
     * @param encryptedID 用户的加密标识符。这应该是一个非空的、有效的加密字符串。
     * @return 返回与给定加密ID关联的Open ID。如果找不到Open ID，
     *         或者加密ID无效，该方法可能返回null或抛出适当的异常。
     */
    String getOpenIDFromEncryptedID(String encryptedID);


    /**
     * 检查指定的加密ID是否在用户表中已经存在。
     * 这个方法用于验证user数据表中是否已经包含了一个特定的加密ID，以避免重复。
     *
     * @param encryptedID 需要检查的加密ID字符串。
     * @return Boolean true 如果存在该加密ID，则返回true；如果不存在，则返回false。
     */
    Boolean ifEncryptedIDExists(String encryptedID);

    /**
     * 根据用户的微信openID更新其加密ID。
     * 此方法用于在user表中更新指定用户的加密ID，通常在用户的加密信息变更时调用。
     *
     * @param userID 用户的微信openID，用作更新的定位条件。
     * @param encryptedID 新的加密ID，将替换当前存储的值。
     */
    void updateEncryptedID(String userID, String encryptedID);

    /**
     * 根据用户的微信openID返回用户是否被拉黑。
     *
     * @param userID 用户的微信openID。
     */
    Boolean isBlocked(String userID);
}
