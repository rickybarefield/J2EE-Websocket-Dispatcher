package com.appagility.j2ee.websocket.dispatcher.operation.executors;


import java.io.IOException;
import java.util.Map;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Subscribe;
import com.appagility.j2ee.websocket.dispatcher.subscription.SubscriptionAndCurrent;

public class SubscribeExecutor extends OperationExecutor<Subscribe>
{
    private final Map<String, RepositoryFactory<?>> repositoryFactoryMap;

    public SubscribeExecutor(Map<String, RepositoryFactory<?>> repositoryFactoryMap)
    {
        this.repositoryFactoryMap = repositoryFactoryMap;
    }

    @Override
    public void execute(Subscribe message, ScrudEndpoint scrudEndpoint) throws IOException
    {
        SubscriptionAndCurrent subscriptionAndCurrent = repositoryFactoryMap.get(message.getResourceType()).create().getAndSubscribe(message.getClientId());
        scrudEndpoint.subscriptionSuccess(message.getClientId(), subscriptionAndCurrent.getCurrent());
        subscriptionAndCurrent.getSubscription().connect(scrudEndpoint);
    }
}
