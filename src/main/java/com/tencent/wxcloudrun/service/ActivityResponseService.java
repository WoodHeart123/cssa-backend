package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.ActivityResponseMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.ActivityResponse;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public interface ActivityResponseService {
    /**
     * create a new ActivityResponse
     *
     * @param ID the ID of the user
     * @param activityResponse the activity response object
     */
    void createResponse(Integer ID,  ActivityResponse activityResponse);

    /**
     * @return a list of activity responses
     * @param activityId the id of the selected activity
     */
    List<ActivityResponse> selectResponseList(Integer activityId);

    /**
     * @return get the content of the activity response
     * @param activityID the id of the selected activity
     */
    ActivityResponse selectResponseContent(Integer activityID);

    /**
     * renew the user's activity response
     *
     * @param activityResponse the activity response object
     */
    void updateResponse(ActivityResponse activityResponse);


    /**
     * delete the user's activity response
     *
     * @param activityID the id of the selected activity
     */
    void deleteResponse(Integer activityID);





}
