package com.appagility.j2ee.websocket.dispatcher.subscription;

import java.util.Set;

public class SubscriptionAndCurrent<ITEM>
{
    private Subscription<ITEM> subscription;
    private Set<ITEM> current;

    public SubscriptionAndCurrent(Subscription<ITEM> subscription, Set<ITEM> current)
    {
        this.subscription = subscription;
        this.current = current;
    }

    public Subscription<ITEM> getSubscription()
    {
        return subscription;
    }

    public Set<ITEM> getCurrent()
    {
        return current;
    }
}
