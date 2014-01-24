package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class SubscribeUnsubscribeBase
{
    protected TestClientEndpoint subscribingClient = new TestClientEndpoint();
    protected TestClientEndpoint manipulatingClient = new TestClientEndpoint();

    @Before
    public void connectEndpoints() throws Exception {

        subscribingClient.connect();
        manipulatingClient.connect();
    }

    @After
    public void disconnectEndpoints() throws Exception {

        subscribingClient.disconnect();
        manipulatingClient.disconnect();
    }

    protected Set<String> getIdsFromSubscriptionSuccess(String subscribeResponse)
    {
        JsonObject resources = new JsonParser().parse(subscribeResponse).getAsJsonObject().getAsJsonObject("resources");

        Set<String> idsFromResources = Sets.newHashSet();
        for (Map.Entry<String, JsonElement> entry : resources.entrySet())
        {
            idsFromResources.add(entry.getKey());
        }
        return idsFromResources;
    }

}
