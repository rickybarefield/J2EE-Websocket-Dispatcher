package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Keys;
import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.subscription.SubscriptionAndCurrent;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

public class SubscribeExecutor extends OperationExecutor
{
    private ResourceConverter resourceConverter;
    private final Map<String, RepositoryFactory<?>> repositoryFactoryMap;

    public SubscribeExecutor(ResourceConverter resourceConverter, Map<String, RepositoryFactory<?>> repositoryFactoryMap)
    {
        this.resourceConverter = resourceConverter;
        this.repositoryFactoryMap = repositoryFactoryMap;
    }

    @Override
    public String getOperationName()
    {
        return Keys.SUBSCRIBE.value();
    }

    @Override
    public void execute(JsonObject jsonObject, ScrudEndpoint scrudEndpoint) throws IOException
    {

        String clientId = jsonObject.get("clientId").getAsString();
        String resourceName = jsonObject.get("type").getAsString();
        RepositoryFactory<Object> repositoryFactory = (RepositoryFactory<Object>) nameToRepositoryFactory.get(resourceName);

        SubscriptionAndCurrent subscriptionAndCurrent = repositoryFactoryMap.get(resourceName).create().getAndSubscribe(clientId);

        scrudEndpoint.resources
        subscriptionAndCurrent.getCurrent()
        session.getBasicRemote().
    }

}
