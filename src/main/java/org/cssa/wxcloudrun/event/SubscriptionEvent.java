package org.cssa.wxcloudrun.event;

import lombok.Getter;
import org.cssa.wxcloudrun.model.Subscription;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubscriptionEvent extends  ApplicationEvent {
    private final Subscription subscription;
    private final boolean subscribe;
    public SubscriptionEvent(Object source, Subscription subscription, boolean subscribe) {
        super(source);
        this.subscription = subscription;
        this.subscribe = subscribe;
    }
}
