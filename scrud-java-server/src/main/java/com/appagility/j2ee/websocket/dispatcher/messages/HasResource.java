package com.appagility.j2ee.websocket.dispatcher.messages;

/**
 * @author rbarefield
 */
public interface HasResource {

    Object getResource();
    void setResource(Object resource);

    String getResourceType();
    void setResourceType(String resourceType);
}
