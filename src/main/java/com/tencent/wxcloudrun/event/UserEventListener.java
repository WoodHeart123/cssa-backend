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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class UserEventListener {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    TemplateEngine templateEngine;
    @EventListener
    @Async
    public void Register(SignupEvent signupEvent){
        SignupInfo signupInfo = signupEvent.getSignupInfo();
        User user = activityMapper.login(signupInfo.getUserID());
        Activity activity= activityMapper.findActivity(signupInfo.getActID());
        if(user.getEmail() == null){
            return;
        }
        Context context = new Context();
        context.setVariable("title",activity.getTitle());
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setMessage(templateEngine.process("ActivityConfirmation",context));
        emailDetail.setReceiver(user.getEmail());
        emailDetail.setSubject(activity.getTitle() + "报名");
        emailService.sendSimpleMail(emailDetail);
    }
}
