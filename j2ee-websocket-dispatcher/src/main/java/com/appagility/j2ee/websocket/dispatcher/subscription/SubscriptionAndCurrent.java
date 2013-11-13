package com.appagility.j2ee.websocket.dispatcher.subscription;

import java.util.Collection;

public class SubscriptionAndCurrent<ITEM>
{
    private Subscription<ITEM> subscription;
    private Collection<ITEM> current;

    public SubscriptionAndCurrent(Subscription<ITEM> subscription, Collection<ITEM> current)
    {
        this.subscription = subscription;
        this.current = current;
    }

    public Subscription<ITEM> getSubscription()
    {
        return subscription;
    }

    public Collection<ITEM> getCurrent()
    {
        return current;
    }
}
