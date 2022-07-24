package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.EmailDetail;
import com.tencent.wxcloudrun.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public Integer sendSimpleMail(EmailDetail emailDetail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetail.getReceiver());
        mailMessage.setSubject(emailDetail.getSubject());
        mailMessage.setText(emailDetail.getMessage());
        mailSender.send(mailMessage);
        return 1;
    }
}
