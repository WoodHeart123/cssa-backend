package org.cssa.wxcloudrun.event;

import lombok.Getter;
import org.cssa.wxcloudrun.model.SignupInfo;
import org.springframework.context.ApplicationEvent;

@Getter
public class SignupEvent extends ApplicationEvent {

    private final SignupInfo signupInfo;

    public SignupEvent(Object source, SignupInfo signupInfo) {
        super(source);
        this.signupInfo = signupInfo;
    }

}
