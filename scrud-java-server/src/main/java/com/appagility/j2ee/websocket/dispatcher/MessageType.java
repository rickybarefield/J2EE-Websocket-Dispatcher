package com.appagility.j2ee.websocket.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Create;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Subscribe;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Unsubscribe;

public enum MessageType
{
    SUBSCRIBE("subscribe", Subscribe.class),
    UNSUBSCRIBE("unsubscribe", Unsubscribe.class),
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
