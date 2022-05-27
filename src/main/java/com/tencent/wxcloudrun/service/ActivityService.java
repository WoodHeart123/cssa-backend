package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
    Response createActivity(Activity activity);

    Response regsiterActivity(Activity activity, String userId);
}
