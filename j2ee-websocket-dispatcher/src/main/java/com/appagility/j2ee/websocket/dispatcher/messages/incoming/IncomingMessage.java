package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.io.IOException;

import com.appagility.j2ee.websocket.dispatcher.Message;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.google.gson.annotations.SerializedName;

/**
 * @author rbarefield
 */
public abstract class IncomingMessage extends Message
{
    @SerializedName("client-id")
    private String clientId;

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientId()
    {
        return clientId;
    }


    public abstract void execute(ExecutorFactory executorFactory, ScrudEndpoint scrudEndpoint) throws IOException;
}
