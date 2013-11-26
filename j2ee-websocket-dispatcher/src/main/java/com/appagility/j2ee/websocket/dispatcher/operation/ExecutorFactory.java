package com.appagility.j2ee.websocket.dispatcher.operation;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.CreationExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.SubscribeExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.UnsubscribeExecutor;
import com.google.common.collect.Lists;

import java.util.*;

public class ExecutorFactory
{
    private final ResourceConverter resourceConverter;
    private final Map<String,RepositoryFactory<?>> nameToResourceFactory;

    public ExecutorFactory(Set<Class<?>> resourceClasses) throws IllegalAccessException, InstantiationException
    {
        Map<Class<?>, WebSocketResource> webSocketResourceMap = new HashMap<>();
        nameToResourceFactory = new HashMap<>();

        for(Class<?> clazz: resourceClasses) {

            WebSocketResource annotation = clazz.getAnnotation(WebSocketResource.class);
            webSocketResourceMap.put(clazz, annotation);
            nameToResourceFactory.put(annotation.name(), annotation.repositoryFactory().newInstance());
        }

        resourceConverter = new ResourceConverter(webSocketResourceMap);
    }

    public Map<String, OperationExecutor> create() {

        List<OperationExecutor> executors = Lists.newArrayList(creationExecutor(), subscribeExecutor(), unsubscribeExecutor());
        Map<String, OperationExecutor> executorMap = new HashMap<>();

        for(OperationExecutor operationExecutor : executors) {

            executorMap.put(operationExecutor.getMessageType(), operationExecutor);
        }

        return executorMap;
    }

    private CreationExecutor creationExecutor() {

        return new CreationExecutor(resourceConverter, nameToResourceFactory);
    }

    private SubscribeExecutor subscribeExecutor() {

        return new SubscribeExecutor(resourceConverter, nameToResourceFactory);
    }

    private UnsubscribeExecutor unsubscribeExecutor() {

        return new UnsubscribeExecutor();
    }
}
