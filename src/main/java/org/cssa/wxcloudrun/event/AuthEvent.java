package org.cssa.wxcloudrun.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthEvent extends ApplicationEvent {

    private final Integer authCode;

    private final String email;

    public AuthEvent(Object source, String email, Integer authCode) {
        super(source);
        this.authCode = authCode;
        this.email = email;
    }

}