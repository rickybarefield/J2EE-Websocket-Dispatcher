package com.appagility.j2ee.websocket.dispatcher;

public interface RepositoryFactory<RESOURCE_TYPE>
{
    SubscribingRepository<?, RESOURCE_TYPE> create();
}
