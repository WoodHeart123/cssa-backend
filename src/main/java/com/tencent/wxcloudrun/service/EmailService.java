package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.EmailDetail;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {

    Integer sendSimpleMail(EmailDetail emailDetail) throws MessagingException;
}
