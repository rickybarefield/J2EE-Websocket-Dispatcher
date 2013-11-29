package com.appagility.j2ee.websocket.dispatcher;

/**
 * @author rbarefield
 */
public enum CommonProperties {

    CLIENT_ID("client-id"),
    RESOURCE_TYPE("resource-type"),
    RESOURCE("resource"),
    MESSAGE_TYPE("message-type");


    private String key;

    private CommonProperties(String key)
    {
        this.key = key;
    }

    public String key()
    {
        return this.key;
    }
}
