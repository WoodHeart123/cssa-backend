package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    Integer createActivity(Activity activity);
}
