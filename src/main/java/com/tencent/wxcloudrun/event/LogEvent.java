package com.tencent.wxcloudrun.event;

import com.tencent.wxcloudrun.model.OperationLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LogEvent extends ApplicationEvent {

    private final OperationLog optLog;

    public LogEvent(Object source, OperationLog optLog) {
        super(source);
        this.optLog = optLog;
    }
}
