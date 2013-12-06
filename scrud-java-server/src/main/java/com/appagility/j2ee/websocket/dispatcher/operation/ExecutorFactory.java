package com.appagility.j2ee.websocket.dispatcher.operation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.CreationExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.SubscribeExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.UnsubscribeExecutor;

public class ExecutorFactory
{
    private final ResourceConverter resourceConverter;
    private CreationExecutor creationExecutor;
    private SubscribeExecutor subscribeExecutor;
    private UnsubscribeExecutor unsubscribeExecutor;

    public ExecutorFactory(Set<Class<?>> resourceClasses) throws IllegalAccessException, InstantiationException
    {
        Map<Class<?>, WebSocketResource> webSocketResourceMap = new HashMap<>();
        Map<String, RepositoryFactory<?>> nameToResourceFactory = new HashMap<>();

        for(Class<?> clazz: resourceClasses) {

            WebSocketResource annotation = clazz.getAnnotation(WebSocketResource.class);
            webSocketResourceMap.put(clazz, annotation);
            nameToResourceFactory.put(annotation.name(), annotation.repositoryFactory().newInstance());
        }

        resourceConverter = new ResourceConverter(webSocketResourceMap);

        creationExecutor = new CreationExecutor(nameToResourceFactory);
        unsubscribeExecutor = new UnsubscribeExecutor();
        subscribeExecutor = new SubscribeExecutor(nameToResourceFactory);
    }

    public ResourceConverter resourceConverter()
    {
        return this.resourceConverter;
    }

    public CreationExecutor creationExecutor()
    {
        return creationExecutor;
    }

    public SubscribeExecutor subscribeExecutor()
    {
        return subscribeExecutor;
    }

    public UnsubscribeExecutor unsubscribeExecutor()
    {
        return unsubscribeExecutor;
    }
}
