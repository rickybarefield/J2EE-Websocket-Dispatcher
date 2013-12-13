package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubscribeIT extends SubscribeUnsubscribeBase
{
    @Test
    public void existingResourceSentThroughOnSubscribe() throws Exception {

        String resource1Response = manipulatingClient.createExpectingSuccess("ExistingResource1");
        String resource2Response = manipulatingClient.createExpectingSuccess("ExistingResource2");

        List<String> createdIds = Lists.transform(Lists.newArrayList(resource1Response, resource2Response), JsonHelpers.ID_OF_RESOURCE);

        String subscribeResponse = subscribingClient.subscribeExpectingSuccess();

        JsonObject resources = new JsonParser().parse(subscribeResponse).getAsJsonObject().getAsJsonObject("resources");

        Set<String> idsFromResources = Sets.newHashSet();
        for(Map.Entry<String, JsonElement> entry : resources.entrySet())
        {
            idsFromResources.add(entry.getKey());
        }

        Assert.assertTrue(idsFromResources.containsAll(createdIds));
    }

    @Test
    public void newResourcesSentThrough() throws Exception {

        subscribingClient.subscribeExpectingSuccess();
        subscribingClient.expectMessages(1);

        String createdResponse = manipulatingClient.createExpectingSuccess("NewResource");

        String idOfCreatedResource = JsonHelpers.ID_OF_RESOURCE.apply(createdResponse);

        JsonObject itemReceivedBySubscriber = new JsonParser().parse(subscribingClient.assertMessagesReceived("Subscribing client did not receive the new Item").get(0)).getAsJsonObject();

        Assert.assertEquals("created", itemReceivedBySubscriber.get("message-type").getAsString());

        Assert.assertEquals(idOfCreatedResource, itemReceivedBySubscriber.get("resource-id").getAsString());

        JsonObject resourceReceivedBySubscriber = itemReceivedBySubscriber.getAsJsonObject("resource");
        Assert.assertEquals(idOfCreatedResource, resourceReceivedBySubscriber.get("id").getAsString());
        Assert.assertEquals("NewResource", resourceReceivedBySubscriber.get("name").getAsString());
    }

    @Test
    public void notificationOfDeleteSentThrough() throws Exception {

        //TODO
    }
}
