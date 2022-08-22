package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    void createActivity(Activity activity);

    List<Activity> getActivityList();

    Integer login(Admin admin);

    Integer checkUsername(String username);

    void register(Admin admin);

    void deleteActivity(String actID);
}
