package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ActivityResponseMapper;
import com.tencent.wxcloudrun.model.ActivityResponse;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.ReturnCode;
import com.tencent.wxcloudrun.service.ActivityResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityResponseServiceImpl implements ActivityResponseService {

    @Autowired
    private ActivityResponseMapper activityResponseMapper;

    @Override
    public Response<ReturnCode> createResponse(ActivityResponse activityResponse) {
        // 如果id已经存在了则update, 否则为create
        if (activityResponse.getUserID() != null) {
            int res = activityResponseMapper.updateResponse(activityResponse);
            if(res > 0){
                return new Response<>(ReturnCode.SUCCESS);
            }
        } else {
            int res = activityResponseMapper.createResponse(activityResponse);
            if(res > 0){
                return new Response<>(ReturnCode.SUCCESS);
            }
        }
        return new Response<>(ReturnCode.UNKNOWN_SERVICE);
    }


    @Override
    public Response<ActivityResponse> selectResponseContent(String userID, Integer activityID) {
        ActivityResponse contents = activityResponseMapper.selectResponseContent(userID, activityID);
        if(contents != null) {
            return new Response<>(contents);
        } else {
            return new Response<>(ReturnCode.NO_SEARCH_RESULT);
        }
    }

    @Override
    public Response<ReturnCode> deleteResponse(Integer id) {
        int res = activityResponseMapper.deleteResponse(id);
        if(res > 0) {
            return new Response<>(ReturnCode.SUCCESS);
        } else {
            return new Response<>(ReturnCode.NO_SEARCH_RESULT);
        }
    }
}
