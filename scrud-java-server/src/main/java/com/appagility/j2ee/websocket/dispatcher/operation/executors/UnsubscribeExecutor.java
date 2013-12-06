package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import java.io.IOException;
import java.util.Map;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Unsubscribe;

public class UnsubscribeExecutor extends OperationExecutor<Unsubscribe>
{
    public UnsubscribeExecutor()
    {
    }

    @Override
    public void execute(Unsubscribe message, ScrudEndpoint scrudEndpoint) throws IOException
    {
        scrudEndpoint.unsubscribeFrom(message.getClientId());
    }
}
