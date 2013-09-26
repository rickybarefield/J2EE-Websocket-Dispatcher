package com.appagility.j2ee.websocket.dispatcher;

public interface ResourceListener<RESOURCE_TYPE>
{
    void notifyCreate(RESOURCE_TYPE resource);
}
