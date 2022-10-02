package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {
    void getAuthCode(String email,Integer authCode);
    Response authSuccess(String userID);
    Response login(String nickname, String userID) throws UnsupportedEncodingException;

    Response updateEmail(String email, String userID);
}
