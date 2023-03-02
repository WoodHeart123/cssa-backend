package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Collect;
import com.tencent.wxcloudrun.model.CollectType;
import com.tencent.wxcloudrun.model.Product;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

@Service
public interface UserService {
    /**
     * 向用户邮箱发送验证码
     * @param email 用户邮箱
     * @param authCode 验证码
     */
    void getAuthCode(String email,Integer authCode);
    Response authSuccess(String userID);
    Response login(String nickname, String userID) throws UnsupportedEncodingException;
    Response updateEmail(String email,Boolean subscribe, String userID);
    Response getLikedCommentList(String userID);
    Response updateAvatar(String userID,Integer avatar);
    Response getMyComment(String userID, Integer offset, Integer limit);
    Response updateComment(String userID,Integer commentID,String comment);
    Response deleteComment(String userID,Integer commentID);
    Response updateWechatID(String wechatID, String userID);
    Response updateNickname(String userID, String nickname);
    Response getMySecondhand(String userID,Integer offset,Integer limit);
    Response updateMySecondHand(String userID, Product product);
    Response deleteMySecondHand(String userID, Integer productID);

    Response setProductTime(Integer productID, String userID, Timestamp time);
    Response collect(Collect collect, Boolean save);
    Response getCollectID(CollectType collectType,String userID);
    Response getCollectList(CollectType collectType,String userID,Integer offset,Integer limit);

}
