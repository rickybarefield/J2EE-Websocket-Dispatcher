package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.IncomingMessage;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public abstract class OperationExecutor<MESSAGE_TYPE extends IncomingMessage>
{
    public abstract void execute(MESSAGE_TYPE message, ScrudEndpoint scrudEndpoint) throws IOException;

    public void handleSessionClose(ScrudEndpoint scrudEndpoint) {

        //nop
    }
}
