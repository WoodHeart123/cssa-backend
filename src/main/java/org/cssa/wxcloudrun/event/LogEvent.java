package org.cssa.wxcloudrun.event;

import lombok.Getter;
import org.cssa.wxcloudrun.model.OperationLog;
import org.springframework.context.ApplicationEvent;

@Getter
public class LogEvent extends ApplicationEvent {

    private final OperationLog optLog;

    public LogEvent(Object source, OperationLog optLog) {
        super(source);
        this.optLog = optLog;
    }
}
