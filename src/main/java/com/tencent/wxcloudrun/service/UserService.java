package com.tencent.wxcloudrun.service;


import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void getAuthCode(String email,Integer authCode);
    Response authSuccess(String userID);
}
