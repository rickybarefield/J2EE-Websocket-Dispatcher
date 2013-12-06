package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.io.IOException;

import com.appagility.j2ee.websocket.dispatcher.CommonProperties;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.google.gson.annotations.SerializedName;

/**
 * @author rbarefield
 */
public class Subscribe extends IncomingMessage
{
    @SerializedName("resource-type")
    private String resourceType;

    public String getResourceType()
    {
        return resourceType;
    }

    public void setResourceType(String resourceType)
    {
        this.resourceType = resourceType;
    }

    @Override
    public void execute(ExecutorFactory executorFactory, ScrudEndpoint scrudEndpoint) throws IOException {

        executorFactory.subscribeExecutor().execute(this, scrudEndpoint);
    }
}
