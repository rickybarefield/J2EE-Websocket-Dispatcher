package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public abstract class OperationExecutor
{

    public abstract String getMessageType();

    public abstract void execute(JsonObject jsonObject, ScrudEndpoint scrudEndpoint) throws IOException;

    public void handleSessionClose(Session session) {

        //nop
    }
}
