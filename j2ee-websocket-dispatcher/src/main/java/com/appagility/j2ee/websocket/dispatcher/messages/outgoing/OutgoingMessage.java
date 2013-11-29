package com.appagility.j2ee.websocket.dispatcher.messages.outgoing;

import com.appagility.j2ee.websocket.dispatcher.messages.Message;

/**
 * @author rbarefield
 */
public abstract class OutgoingMessage extends Message
{
    private String messageType;

    protected OutgoingMessage(String messageType)
    {
        this.messageType = messageType;
    }

    @Override
    public String getMessageType()
    {
        return this.messageType;
    }

    @Override
    public void setMessageType(String messageType)
    {
        throw new UnsupportedOperationException("Cannot set the type of an outgoing message");
    }
}
