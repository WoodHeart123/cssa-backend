package com.tencent.wxcloudrun.dao;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.model.Activity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    Integer createActivity(Activity activity);

    Activity getActivity(String actID); // will be changed to findActivity(id);

    Integer updateActivity(Activity activity);



}
