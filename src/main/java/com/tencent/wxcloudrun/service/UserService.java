package com.tencent.wxcloudrun.service;


import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void getAuthCode(String email,Integer authCode);
}
