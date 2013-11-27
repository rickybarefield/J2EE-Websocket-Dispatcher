package com.appagility.j2ee.websocket.dispatcher;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.websocket.RemoteEndpoint;
import java.io.IOException;
import java.util.Collection;

public class ScrudEndpoint
{
    RemoteEndpoint.Basic remoteEndpoint;


    public ScrudEndpoint(RemoteEndpoint.Basic remoteEndpoint)
    {
        this.remoteEndpoint = remoteEndpoint;
    }

    private void addMessageType(JsonObject json, MessageType messageType)
    {
        json.addProperty("message-type", messageType.type());
    }


    public void created(String clientSubscriptionId, Object createdResource) throws IOException
    {
        clientIdAndResource(MessageType.CREATED, clientSubscriptionId, createdResource);
    }

    public void createSuccess(String clientId, Object createdResource) throws IOException
    {
        clientIdAndResource(MessageType.CREATE_SUCCESS, clientId, createdResource);
    }

    private void clientIdAndResource(MessageType messageType, String clientId, Object resource) throws IOException
    {
        Gson gson = new Gson();
        JsonObject itemJson = gson.toJsonTree(resource).getAsJsonObject();

        JsonObject object = new JsonObject();
        addMessageType(object, messageType);
        object.addProperty("client-id", clientId);
        object.add("resource", itemJson);

        remoteEndpoint.sendText(object.toString());
    }

    public void subscriptionSuccess(String clientSubscriptionId, Collection<Object> existingResources)
    {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        addMessageType(object, MessageType.SUBSCRIPTION_SUCCESS);
        object.addProperty("client-id", clientSubscriptionId);
        //TODO You are here, need to create resources as array then call from SubscribeExecutor
        //TODO SubscribeIT needs to work
    }

}
