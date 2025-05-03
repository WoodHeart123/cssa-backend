package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.EmailDetail;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendSimpleMail(EmailDetail emailDetail) throws MessagingException;
}
