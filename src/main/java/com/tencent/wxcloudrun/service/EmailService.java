package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.EmailDetail;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    Integer sendSimpleMail(EmailDetail emailDetail);
}
