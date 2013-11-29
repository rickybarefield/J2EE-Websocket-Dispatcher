package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.io.IOException;

import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.google.gson.annotations.SerializedName;

/**
 * @author rbarefield
 */
public class Create extends IncomingMessage implements HasResource
{
    @SerializedName("resource-type")
    private String resourceType;

    private Object resource;

    @Override
    public void execute(ExecutorFactory executorFactory, ScrudEndpoint scrudEndpoint) throws IOException
    {
        executorFactory.creationExecutor().execute(this, scrudEndpoint);
    }

    public String getResourceType()
    {
        return resourceType;
    }

    public void setResourceType(String resourceType)
    {
        this.resourceType = resourceType;
    }

    public Object getResource()
    {
        return resource;
    }

    public void setResource(Object resource)
    {
        this.resource = resource;
    }
}
