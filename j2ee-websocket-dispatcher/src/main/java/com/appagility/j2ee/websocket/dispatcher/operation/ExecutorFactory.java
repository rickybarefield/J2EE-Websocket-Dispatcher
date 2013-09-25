package com.appagility.j2ee.websocket.dispatcher.operation;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.CreationExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.ReadExecutor;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.ResourceListingExecutor;
import com.google.common.collect.Lists;

import java.util.*;

public class ExecutorFactory
{
    private final ResourceConverter resourceConverter;
    private final Map<String,RepositoryFactory<?>> nameToResourceFactory;
    private final Set<WebSocketResource> resourceAnnotations;

    public ExecutorFactory(Set<Class<?>> resourceClasses) throws IllegalAccessException, InstantiationException
    {

        resourceAnnotations = new HashSet<WebSocketResource>();
        Map<Class<?>, WebSocketResource> webSocketResourceMap = new HashMap<>();
        nameToResourceFactory = new HashMap<>();

        for(Class<?> clazz: resourceClasses) {

            WebSocketResource annotation = clazz.getAnnotation(WebSocketResource.class);
            resourceAnnotations.add(annotation);
            webSocketResourceMap.put(clazz, annotation);
            nameToResourceFactory.put(annotation.name(), annotation.repositoryFactory().newInstance());
        }

        resourceConverter = new ResourceConverter(webSocketResourceMap);
    }

    public Map<String, OperationExecutor> create() {

        List<OperationExecutor> executors = Lists.newArrayList(resourceListingExecutor(), creationExecutor(), readExecutor());
        Map<String, OperationExecutor> executorMap = new HashMap<>();

        for(OperationExecutor operationExecutor : executors) {

            executorMap.put(operationExecutor.getOperationName(), operationExecutor);
        }

        return executorMap;
    }

    private ResourceListingExecutor resourceListingExecutor() {

        return new ResourceListingExecutor(resourceAnnotations);
    }

    private CreationExecutor creationExecutor() {

        return new CreationExecutor(resourceConverter, nameToResourceFactory);
    }

    private ReadExecutor readExecutor() {

        return new ReadExecutor(resourceConverter, nameToResourceFactory);
    }

}
