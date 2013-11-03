package com.appagility.j2ee.websocket.dispatcher;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.websocket.RemoteEndpoint;
import java.io.IOException;

public class ScrudEndpoint
{
    RemoteEndpoint.Basic remoteEndpoint;

    public void created(String clientSubscriptionId, Object item) throws IOException
    {
        Gson gson = new Gson();
        JsonObject itemJson = gson.toJsonTree(item).getAsJsonObject();

        JsonObject object = new JsonObject();
        object.addProperty("client-subscription-id", clientSubscriptionId);
        object.add("data", itemJson);

        remoteEndpoint.sendText(object.getAsString());
    }
}
