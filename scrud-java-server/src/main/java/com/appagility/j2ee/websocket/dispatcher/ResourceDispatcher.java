package com.appagility.j2ee.websocket.dispatcher;

public class ResourceDispatcher<RESOURCE_TYPE>
{
    private final String resourceName;
    private final RepositoryFactory<RESOURCE_TYPE> repositoryFactory;

    public ResourceDispatcher(Class<RESOURCE_TYPE> resourceClass) throws IllegalAccessException, InstantiationException
    {
        WebSocketResource resourceAnnotation = resourceClass.getAnnotation(WebSocketResource.class);
        resourceName = resourceAnnotation.name();
        repositoryFactory = (RepositoryFactory<RESOURCE_TYPE>) resourceAnnotation.repositoryFactory().newInstance();
    }



}
