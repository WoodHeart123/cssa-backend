package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.ActivityResponse;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.ReturnCode;
import java.util.List;

public interface ActivityResponseService {
    /**
     * create a new ActivityResponse
     *
     * @param activityResponse the activity response object
     */
    Response<ReturnCode> createResponse(ActivityResponse activityResponse);



    /**
     * @return get the content of the activity response
     * @param activityID the id of the selected activity
     */
    Response<ActivityResponse> selectResponseContent(String userID, Integer activityID);

    /**
     * delete the user's activity response
     *
     * @param id the id of the selected activity response
     */
    Response<ReturnCode> deleteResponse(Integer id);

}
