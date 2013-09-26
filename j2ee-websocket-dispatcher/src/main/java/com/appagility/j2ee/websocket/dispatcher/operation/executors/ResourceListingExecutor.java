package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

public class ResourceListingExecutor extends OperationExecutor
{

    private final JsonArray jsonElements;

    public ResourceListingExecutor(Set<WebSocketResource> resourceAnnotations) {

        jsonElements = new JsonArray();

        for(WebSocketResource resourceAnnotation : resourceAnnotations) {

            jsonElements.add(new JsonPrimitive(resourceAnnotation.name()));
        }
    }

    @Override
    public String getOperationName()
    {
        return "list-resources";
    }

    @Override
    public void execute(JsonObject jsonObject, Session session) throws IOException
    {
        session.getBasicRemote().sendText(jsonElements.toString());
    }
}
