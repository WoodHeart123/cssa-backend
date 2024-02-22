package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cssa.wxcloudrun.dao.ActivityMapper;
import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.PaymentOption;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.cssa.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Response<SignupInfo> checkSignup(String userID, Integer actID) {
        SignupInfo result = activityMapper.checkRegistration(userID, actID);
        if (result == null) {
            result = new SignupInfo();
            result.setIfJoined(false);
        } else {
            result.setIfJoined(true);
        }
        return new Response<>(result);
    }


    @Override
    public Response<List<Activity>> getActivityList() {
        List<Activity> activityList = activityMapper.getActivityList();
        for (Activity activity : activityList) {
            activity.setPayment(JSON.parseObject(activity.getPaymentJSON(), PaymentOption.class));
            activity.setImages((ArrayList<String>) JSON.parseArray(activity.getImagesJSON(), String.class));
        }
        return new Response<>(activityList);
    }

    @Override
    public Response<Object> registerActivity(SignupInfo signupInfo) {
        activityMapper.registerActivity(signupInfo);
        return new Response<>();
    }

    @Override
    public Response<Object> cancelRegister(String userID, Integer actID) {
        activityMapper.cancelRegister(userID, actID);
        return new Response<>();
    }

    @Override
    public Response<List<Activity>> getRegisterList(String userID) {
        List<Activity> activityList = activityMapper.getRegisterList(userID);
        return new Response<>(activityList);
    }

    @Override
    public  Response<Activity> findDeletedActivity(Integer actID) {
        Activity deletedActivity = activityMapper.findDeletedActivity(actID);
        if (deletedActivity == null) return new Response<>();
        return new Response<>(deletedActivity);
    }

    @Override
    public Response<Activity> getActivityDetails(Integer actID) {
        Activity activity = activityMapper.getActivityDetails(actID);
        if (activity != null) {
            return new Response<>(activity);
        } else {
            return new Response<>();
        }
    }

    @Override
    public Response<List<Activity>> getOngoingActivities() {
        List<Activity> ongoingActivities = activityMapper.getOngoingActivities();
        return new Response<>(ongoingActivities);
    }

    @Override
    public Response<String> updateFullActivity(Integer actID, Activity updatedActivity) {
        if (updatedActivity.getImages() == null || updatedActivity.getImages().isEmpty()) {
            updatedActivity.setImagesJSON("{}");
        }

        if (updatedActivity.getPayment() == null) updatedActivity.setPaymentJSON("{}");

        Activity deletedActivity = activityMapper.findDeletedActivity(actID);
        if (deletedActivity != null) {
            if (updatedActivity.getTitle() == null || updatedActivity.getTitle().isEmpty()) {
                updatedActivity.setTitle(deletedActivity.getTitle());
            }

            if (updatedActivity.getDescription() == null || updatedActivity.getDescription().isEmpty()) {
                updatedActivity.setDescription(deletedActivity.getDescription());
            }

            if (updatedActivity.getStartDate() == null) {
                updatedActivity.setStartDate(deletedActivity.getStartDate());
            }

            if (updatedActivity.getEndDate() == null) {
                updatedActivity.setEndDate(deletedActivity.getEndDate());
            }

            if (updatedActivity.getDeadline() == null) {
                updatedActivity.setDeadline(deletedActivity.getDeadline());
            }

            if (updatedActivity.getLocation() == null || updatedActivity.getLocation().isEmpty()) {
                updatedActivity.setLocation(deletedActivity.getLocation());
            }

            if (updatedActivity.getImages() == null || updatedActivity.getImages().isEmpty()) {
                if (deletedActivity.getImages() != null && !deletedActivity.getImages().isEmpty()) {
                    updatedActivity.setImages(deletedActivity.getImages());
                    updatedActivity.setImagesJSON(JSON.toJSONString(updatedActivity.getImages()));
                }
            }

            if (updatedActivity.getPayment() == null && deletedActivity.getPayment() != null) {
                updatedActivity.setPayment(deletedActivity.getPayment());
                updatedActivity.setPaymentJSON(JSON.toJSONString(updatedActivity.getPayment()));
            }

            if (updatedActivity.getPrice() == null) updatedActivity.setPrice(deletedActivity.getPrice());

            if (updatedActivity.getAdditionalInfoJSON() == null || updatedActivity.getAdditionalInfoJSON().isBlank()) {
                updatedActivity.setAdditionalInfoJSON(deletedActivity.getAdditionalInfoJSON());
            }
        } else {
            return new Response<>();
        }
        activityMapper.updateFullActivity(actID, updatedActivity);

        return new Response<>("活动更新成功");
    }

    @Override
    public Response<String> deleteActivity(Integer actID) {
        if (activityMapper.findDeletedActivity(actID) != null) return new Response<>();
        activityMapper.deleteActivity(actID);
        return new Response<>("活动删除成功");
    }

}
