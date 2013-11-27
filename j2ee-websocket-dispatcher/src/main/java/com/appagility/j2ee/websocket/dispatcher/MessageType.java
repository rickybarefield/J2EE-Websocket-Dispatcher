package com.appagility.j2ee.websocket.dispatcher;

/**
 * Created with IntelliJ IDEA.
 * User: ricky
 * Date: 26/11/13
 * Time: 18:43
 * To change this template use File | Settings | File Templates.
 */
public enum MessageType
{
    SUBSCRIBE("subscribe"),

    SUBSCRIPTION_SUCCESS("subscription-success"),

    CREATED("created"),

    CREATE_SUCCESS("create-success");

    private String messageType;

    private MessageType(String messageType)
    {
        this.messageType = messageType;
    }

    public String type()
    {
        return messageType;
    }
}
