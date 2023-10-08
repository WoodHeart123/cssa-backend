package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.ActivityResponse;
import com.tencent.wxcloudrun.model.SignupInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ActivityResponseMapper {

    /**
     * create a new ActivityResponse
     *
     * @param userID the ID of the user
     * @param activityResponse the activity response object
     */
    void createResponse(String userID,  ActivityResponse activityResponse);

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
