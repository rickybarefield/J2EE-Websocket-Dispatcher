package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

public class SubscribeIT extends SubscribeUnsubscribeBase
{
    @Test
    public void existingResourceSentThroughOnSubscribe() throws Exception {

        String resource1Response = manipulatingClient.createExpectingSuccess("ExistingResource1");
        String resource2Response = manipulatingClient.createExpectingSuccess("ExistingResource2");

        List<Long> createdIds = Lists.transform(Lists.newArrayList(resource1Response, resource2Response), JsonHelpers.ID_OF_RESOURCE);

        String subscribeResponse = subscribingClient.subscribeExpectingSuccess();

        JsonArray resources = new JsonParser().parse(subscribeResponse).getAsJsonObject().getAsJsonArray("resources");

        List<Long> idsFromResources = Lists.newArrayList(Iterables.transform(resources, JsonHelpers.ID_OF));

        Assert.assertTrue(idsFromResources.containsAll(createdIds));
    }

    @Test
    public void newResourcesSentThrough() throws Exception {

//        String subscribeResponse = subscribe();
//        JsonHelpers.assertSuccess(subscribeResponse);
//
//        subscribingClient.expectMessages(1);
//
//        Long idFromCreateResponse = createWithManipulator("ManipulatorCreated1");
//
//        JsonObject itemReceivedBySubscriber = new JsonParser().parse(subscribingClient.assertMessagesReceived("Subscribing client did not receive the new Item").get(0)).getAsJsonObject();
//
//        Assert.assertEquals("create", itemReceivedBySubscriber.get("operation").getAsString());
//
//        JsonObject resourceReceivedBySubscriber = itemReceivedBySubscriber.getAsJsonObject("resource");
//        Assert.assertEquals(idFromCreateResponse, resourceReceivedBySubscriber.get("id").getAsLong(), 0);
//        Assert.assertEquals("ManipulatorCreated1", resourceReceivedBySubscriber.get("name").getAsString());
    }

    @Test
    public void notificationOfDeleteSentThrough() throws Exception {

    }
}
