package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscribeIT
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
    public void existingResourceSentThroughOnSubscribe() throws Exception {

        manipulatingClient.expectMessages(2);
        manipulatingClient.sendMessage("{operation: 'create', type: 'Item', resource: {name: 'ExistingResource1'}}");
        manipulatingClient.sendMessage("{operation: 'create', type: 'Item', resource: {name: 'ExistingResource2'}}");
        List<String> createResponses = manipulatingClient.assertMessagesReceived("Did not receive creation responses");

        List<Long> createdIds = Lists.newArrayList(Iterables.transform(createResponses, JsonHelpers.ID_OF_RESOURCE));

        subscribingClient.expectMessages(1);
        subscribingClient.sendMessage("{operation: 'subscribe', type: 'Item'}");
        String subscribeResponse = subscribingClient.assertMessagesReceived("No response received for subscribe").get(0);
        JsonHelpers.assertSuccess(subscribeResponse);

        JsonArray resources = new JsonParser().parse(subscribeResponse).getAsJsonObject().getAsJsonArray("resources");

        List<Long> idsFromResources = Lists.newArrayList(Iterables.transform(resources, JsonHelpers.ID_OF));

        Assert.assertTrue(idsFromResources.containsAll(createdIds));
    }

    @Test
    public void newResourcesSentThrough() throws Exception {

        subscribingClient.expectMessages(1);
        subscribingClient.sendMessage("{operation: 'subscribe', type: 'Item'}");
        String subscribeResponse = subscribingClient.assertMessagesReceived("No response received for subscribe").get(0);
        JsonHelpers.assertSuccess(subscribeResponse);

        subscribingClient.expectMessages(1);

        manipulatingClient.expectMessages(1);
        manipulatingClient.sendMessage("{operation: 'create', type: 'Item', resource: {name: 'ManipulatorCreated1'}}");

        String createResponse = manipulatingClient.assertMessagesReceived("No response to the create").get(0);

        Long idFromCreateResponse = new JsonParser().parse(createResponse).getAsJsonObject().getAsJsonObject("resource").get("id").getAsLong();

        JsonObject itemReceivedBySubscriber = new JsonParser().parse(subscribingClient.assertMessagesReceived("Subscribing client did not receive the new Item").get(0)).getAsJsonObject();

        Assert.assertEquals("create", itemReceivedBySubscriber.get("operation").getAsString());

        JsonObject resourceReceivedBySubscriber = itemReceivedBySubscriber.getAsJsonObject("resource");
        Assert.assertEquals(idFromCreateResponse, resourceReceivedBySubscriber.get("id").getAsLong(), 0);
        Assert.assertEquals("ManipulatorCreated1", resourceReceivedBySubscriber.get("name").getAsString());
    }

    @Test
    public void notificationOfDeleteSentThrough() throws Exception {

    }
}
