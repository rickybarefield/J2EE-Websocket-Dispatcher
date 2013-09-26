package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public abstract class OperationExecutor
{

    public abstract String getOperationName();

    public abstract void execute(JsonObject jsonObject, Session session) throws IOException;

    public void handleSessionClose(Session session) {

        //nop
    }
}
