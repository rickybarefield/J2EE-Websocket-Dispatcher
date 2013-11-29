package com.appagility.j2ee.websocket.dispatcher;

import com.google.gson.annotations.SerializedName;

/**
 * @author rbarefield
 */
public abstract class Message
{
    @SerializedName("message-type")
    private String messageType;

    public String getMessageType()
    {
        return messageType;
    }

    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }
}
