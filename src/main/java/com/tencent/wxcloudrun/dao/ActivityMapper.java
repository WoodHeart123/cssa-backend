package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ActivityMapper {
    void createActivity(Activity activity);
    ArrayList<SignupInfo> checkSignup(String userID, Long date);
}