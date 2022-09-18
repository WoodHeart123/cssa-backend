package com.tencent.wxcloudrun.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthEvent extends ApplicationEvent {

    private Integer authCode;

    private String email;

    public AuthEvent(Object source,String email, Integer authCode){
        super(source);
        this.authCode = authCode;
        this.email = email;
    }

}