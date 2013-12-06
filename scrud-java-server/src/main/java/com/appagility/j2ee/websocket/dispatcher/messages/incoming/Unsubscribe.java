package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.io.IOException;

import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;

/**
 * @author rbarefield
 */
public class Unsubscribe extends IncomingMessage
{
    @Override
    public void execute(ExecutorFactory executorFactory, ScrudEndpoint scrudEndpoint) throws IOException
    {
        executorFactory.unsubscribeExecutor().execute(this, scrudEndpoint);
    }
}
