package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubscribeTest
{
    private TestClientEndpoint subscribingClient = new TestClientEndpoint();
    private TestClientEndpoint manipulatingClient = new TestClientEndpoint();

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

    @Test
    public void existingResourceSentThroughOnSubscribe() {

    }

    @Test
    public void newResourcesSentThrough() throws Exception {

        subscribingClient.expectMessages(1);
        subscribingClient.sendMessage("{operation: 'subscribe', type: 'Item'}");
        String subscribeResponse = subscribingClient.assertMessagesReceived("No response received for subscribe").get(0);
        AssertionHelpers.assertSuccess(subscribeResponse);

        subscribingClient.expectMessages(1);
        manipulatingClient.expectMessages(1);

        manipulatingClient.sendMessage("{operation: 'create', type: 'Item', resource: {name: 'ManipulatorCreated1'}");

        String createResponse = manipulatingClient.assertMessagesReceived("No response to the create").get(0);

        Long idFromCreateResponse = new JsonParser().parse(createResponse).getAsJsonObject().getAsJsonObject("resource").get("id").getAsLong();

        JsonObject itemReceivedBySubscriber = new JsonParser().parse(subscribingClient.assertMessagesReceived("Subscribing client did not receive the new Item").get(0)).getAsJsonObject();

        Assert.assertEquals("create", itemReceivedBySubscriber.get("operation"));

        JsonObject resourceReceivedBySubscriber = itemReceivedBySubscriber.getAsJsonObject("resource");
        Assert.assertEquals(idFromCreateResponse, resourceReceivedBySubscriber.get("id").getAsLong(), 0);
        Assert.assertEquals("ManipulatorCreated1", resourceReceivedBySubscriber.get("name").getAsString());
    }

    @Test
    public void notificationOfDeleteSentThrough() throws Exception {

    }
}
