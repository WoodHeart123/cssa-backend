package com.tencent.wxcloudrun.event;

import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.EmailDetail;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    EmailService emailService;

    @EventListener
    @Async
    public void Register(SignupEvent signupEvent){
        SignupInfo signupInfo = signupEvent.getSignupInfo();
        User user = activityMapper.login(signupInfo.getUserID());
        if(user.getEmail() == null){
            return;
        }
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setMessage("报名成功");
        emailDetail.setReceiver(user.getEmail());
        emailDetail.setSubject("活动报名");
        emailService.sendSimpleMail(emailDetail);
    }
}
