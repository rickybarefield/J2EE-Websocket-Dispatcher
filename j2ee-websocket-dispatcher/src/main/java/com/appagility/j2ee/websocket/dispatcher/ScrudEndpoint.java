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

    public void created(String clientSubscriptionId, Object item) throws IOException
    {
        Gson gson = new Gson();
        JsonObject itemJson = gson.toJsonTree(item).getAsJsonObject();

        JsonObject object = new JsonObject();
        object.addProperty("client-subscription-id", clientSubscriptionId);
        object.add("resource", itemJson);

        remoteEndpoint.sendText(object.getAsString());
    }

    public void subscriptionSuccess(String clientSubscriptionId, Collection<Object> existingResources)
    {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        object.addProperty("client-subscription-id", clientSubscriptionId);
        //TODO You are here, need to create resources as array then call from SubscribeExecutor
        //TODO SubscribeIT needs to work
    }
}
