package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Keys;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public class UnsubscribeExecutor extends OperationExecutor
{
    @Override
    public String getMessageType()
    {
        return "unsubscribe";
    }

    @Override
    public void execute(JsonObject jsonObject, ScrudEndpoint scrudEndpoint) throws IOException
    {
        //TODO
    }

    @Override
    public void handleSessionClose(Session session)
    {
        //TODO
    }
}
