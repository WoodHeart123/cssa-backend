package org.cssa.wxcloudrun.event;

import org.cssa.wxcloudrun.dao.ActivityMapper;
import org.cssa.wxcloudrun.dao.UserMapper;
import org.cssa.wxcloudrun.model.Activity;
import org.cssa.wxcloudrun.model.EmailDetail;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.cssa.wxcloudrun.model.Subscription;
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
        User user = userMapper.getUserByOpenID(signupInfo.getUserID());
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

    /**
     * 处理订阅和取消订阅事件。
     *
     * 该方法是一个事件监听器，异步处理订阅和取消订阅事件，根据事件的类型（订阅或取消订阅），
     * 发送相应的确认邮件给用户。
     *
     * @param event 包含订阅信息的事件对象。此参数包括用户的订阅信息和事件类型（订阅或取消订阅）。
     */
    @EventListener
    @Async
    public void handleSubscribe( SubscriptionEvent event) {
        Subscription subscriptionInfo = event.getSubscription();
        String userID = subscriptionInfo.getOpenID();
        String receiver = subscriptionInfo.getEmail();
        if (userID.isBlank() || receiver.isBlank()) return;

        EmailDetail emailDetail = new EmailDetail();
        Context context = new Context(); // 因为templateEngine.process()必须要有一个IContext，但订阅确认和取消邮件不需要添加变量。

        if (event.isSubscribe()) {
            // 处理订阅确认
            String encryptedID = userID; // 如果处理订阅，userID应为加密ID
            // context.setVariable("encryptedID", encryptedID); 这里先注释掉。未来做网页发送退顶请求后再取消注释。
            System.out.println("加密ID："+encryptedID);
            emailDetail.setMessage(templateEngine.process("SubscriptionConfirmed", context));
            emailDetail.setSubject("【CSSA订阅】邮件订阅确认");
            emailDetail.setReceiver(receiver);
            try {
                emailService.sendSimpleMail(emailDetail);
                System.out.println("邮件发送成功。");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            // 处理退订确认
            emailDetail.setMessage(templateEngine.process("SubscriptionCancelled", context));
            emailDetail.setSubject("【CSSA订阅】邮件退订确认");
            emailDetail.setReceiver(receiver);
            try {
                emailService.sendSimpleMail(emailDetail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
