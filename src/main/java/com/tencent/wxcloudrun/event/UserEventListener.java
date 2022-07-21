package com.tencent.wxcloudrun.event;

import com.tencent.wxcloudrun.model.SignupInfo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @EventListener
    public void Register(SignupEvent signupEvent){
        SignupInfo signupInfo = signupEvent.getSignupInfo();
        // TODO
    }
}
