package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Info;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Transactional
    @Override
    public Response createActivity(Activity activity) {
        if (activity.getAdditionalInfo() == null) {
            activity.setAdditionalInfo(new ArrayList<>());
        }
        activity.setUserJoined(new ArrayList<>()); // does not compile
        activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
        activity.setUserJoinedListJSON(JSON.toJSONString(activity.getUserJoined())); // does not compile
        activityMapper.createActivity(activity);
        return Response.builder().status(100).message("成功").build();
    }

    @Override
    public Response checkSignup(String actID, String userID, Long Date) {
        ArrayList<SignupInfo> result = activityMapper.checkSignup(actID, Date);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getActID().equals(actID)) {
                result.get(i).setDiscount(1 - (result.size() - 1) * 0.1);
                return Response.builder().status(100).message("").data(result.get(i)).build();
            }
        }
        SignupInfo temp = new SignupInfo();
        temp.setIfJoined(false);
        temp.setDiscount(1.0);
        return Response.builder().status(100).data(temp).build();
    }

    @Override
    public Response regsiterActivity(String actID, String[] notes) {
        try {
            Activity activity = activityMapper.getActivity(actID); // will be changed to findActivity(id);

            // edge cases
            if (activity == null) {
                return Response.builder().status(101).message("活动不存在").build();
            }
            if (activity.getCapacity() <= activity.getUserJoinedNum()) {
                return Response.builder().status(101).message("活动人数已满").build();
            }
            for (Info i : activity.getAdditionalInfo()) {
                if (i.name.equalsIgnoreCase(notes[0])) {
                    return Response.builder().status(101).message("已报名").build();
                }
            }

            // TODO: 注册活动
            // activity.getAdditionalInfo().add(new Info(notes[0], notes[1]));
            // System.out.println(activity.getAdditionalInfo());
            // System.out.println(notes);

            Info info = new Info();
            info.name = notes[0]; // 姓名，或第一个文本输入框
            info.value = notes[1]; // 值，或第二个多选器

            activity.getAdditionalInfo().add(info);
            // append info to AdditionalInfoJSON
            activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
            activity.setUserJoinedNum(activity.getUserJoinedNum() + 1);
            activityMapper.updateActivity(activity);

            return Response.builder().status(100).message("成功").build();

        } catch (Exception exception) {
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }
}
