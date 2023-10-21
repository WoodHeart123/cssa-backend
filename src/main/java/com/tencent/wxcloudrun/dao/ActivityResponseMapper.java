package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.ActivityResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ActivityResponseMapper {

    /**
     * create a new ActivityResponse
     *
     * @param activityResponse the activity response object
     */
    int createResponse(ActivityResponse activityResponse); // 返回状态码



    /**
     * @return get the content of the activity response
     * @param activityID the id of the selected activity
     */
    ActivityResponse selectResponseContent(String userID, Integer activityID);

    /**
     * renew the user's activity response
     *
     * @param activityResponse the activity response object
     */
    int updateResponse(ActivityResponse activityResponse); // 返回状态码

    /**
     * delete the user's activity response
     *
     * @param id the id of the selected activity response
     */
    int deleteResponse(Integer id);  // 返回状态码

}
