package com.tencent.wxcloudrun.event;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.SignupInfo;
import com.tencent.wxcloudrun.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SignupEvent extends ApplicationEvent {

    private SignupInfo signupInfo;

    public SignupEvent(Object source, SignupInfo signupInfo){
        super(source);
        this.signupInfo = signupInfo;
    }

}
