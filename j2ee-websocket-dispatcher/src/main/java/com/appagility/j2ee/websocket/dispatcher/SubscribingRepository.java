package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.subscription.Subscription;
import com.appagility.j2ee.websocket.dispatcher.subscription.SubscriptionAndCurrent;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class SubscribingRepository<ID, ITEM>
{
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Map<String, Subscription> subscriptions = new HashMap<>();

    public SubscriptionAndCurrent getAndSubscribe(String clientId)
    {
        try
        {
            readWriteLock.readLock().lock();
            Collection<ITEM> current = readAll();
            Subscription<ITEM> subscription = new Subscription<>(clientId);
            subscriptions.put(subscription.getServerId(), subscription);

            return new SubscriptionAndCurrent(subscription, current);
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    ITEM doCreate(ITEM item)
    {
        try
        {
            readWriteLock.writeLock().lock();

            ITEM created = create(item);

            //TODO This will need to have a better locking algorithm, currently very slow
            //TODO This will also need to be asynchronous
            for(Subscription<ITEM> subscription : subscriptions.values())
            {
                try
                {
                    subscription.created(item);
                }
                catch (IOException e)
                {
                    //TODO Think
                }
            }

            return created;
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }

    }

    public abstract ITEM create(ITEM item);
    protected abstract Collection<ITEM> readAll();
    //TODO read / update / delete
}
