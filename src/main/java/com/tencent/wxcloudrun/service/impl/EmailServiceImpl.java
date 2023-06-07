package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.EmailDetail;
import com.tencent.wxcloudrun.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EmailDetail emailDetail) throws MessagingException {
        logger.info("sending email to " + emailDetail.getReceiver());
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMessageHelper = new MimeMessageHelper(mailMessage, true);
        mailMessageHelper.setFrom(sender);
        mailMessageHelper.setTo(emailDetail.getReceiver());
        mailMessageHelper.setSubject(emailDetail.getSubject());
        mailMessageHelper.setText(emailDetail.getMessage(), true);
        mailSender.send(mailMessage);
    }
}
