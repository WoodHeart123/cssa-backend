package org.cssa.wxcloudrun.event;

import org.cssa.wxcloudrun.dao.ActivityMapper;
import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.EmailDetail;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.cssa.wxcloudrun.model.User;
import org.cssa.wxcloudrun.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class UserEventListener {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    TemplateEngine templateEngine;

    @EventListener
    @Async
    public void register(SignupEvent signupEvent) {
        SignupInfo signupInfo = signupEvent.getSignupInfo();
        User user = userMapper.login(signupInfo.getUserID());
        Activity activity = activityMapper.findActivity(signupInfo.getActID());
        if (user.getEmail() == null) {
            return;
        }
        Context context = new Context();
        context.setVariable("title", activity.getTitle());
        context.setVariable("location", activity.getLocation());
        context.setVariable("startDate", activity.getStartDate().toString());
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setMessage(templateEngine.process("ActivityConfirmation", context));
        emailDetail.setReceiver(user.getEmail());
        emailDetail.setSubject(activity.getTitle() + "报名");
        try {
            emailService.sendSimpleMail(emailDetail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @EventListener
    @Async
    public void getAuthCode(AuthEvent authEvent) {
        Integer authCode = authEvent.getAuthCode();
        String email = authEvent.getEmail();
        Context context = new Context();
        context.setVariable("authCode", authCode);
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setMessage(templateEngine.process("SendAuthCode", context));
        emailDetail.setReceiver(email);
        emailDetail.setSubject("验证码");
        try {
            emailService.sendSimpleMail(emailDetail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
