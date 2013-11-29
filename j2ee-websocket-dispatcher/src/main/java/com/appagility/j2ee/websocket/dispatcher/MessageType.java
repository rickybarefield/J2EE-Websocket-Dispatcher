package com.appagility.j2ee.websocket.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Create;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Subscribe;

/**
 * Created with IntelliJ IDEA.
 * User: ricky
 * Date: 26/11/13
 * Time: 18:43
 * To change this template use File | Settings | File Templates.
 */
public enum MessageType
{
    SUBSCRIBE("subscribe", Subscribe.class),

    SUBSCRIPTION_SUCCESS("subscription-success", null),

    CREATE("create", Create.class),

    CREATED("created", null),

    CREATE_SUCCESS("create-success", null);

    private String messageType;
    private Class<?> type;

    private static Map<String, MessageType> keyToMessageType = new HashMap<>();

    static
    {
        for(MessageType messageType : values())
        {
            keyToMessageType.put(messageType.key(), messageType);
        }
    }

    public static MessageType forKey(String key)
    {
        return keyToMessageType.get(key);
    }

    private MessageType(String messageType, Class<?> type)
    {
        this.messageType = messageType;
        this.type = type;
    }

    public String key()
    {
        return messageType;
    }

    public Class<?> type()
    {
        return type;
    }
}
