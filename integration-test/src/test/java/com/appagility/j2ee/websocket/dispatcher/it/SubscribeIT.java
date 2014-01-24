package com.appagility.j2ee.websocket.dispatcher.it;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

public class SubscribeIT extends SubscribeUnsubscribeBase
{
    @Test
    public void existingResourceSentThroughOnSubscribe() throws Exception
    {

        String resource1Response = manipulatingClient.createExpectingSuccess("ExistingResource1");
        String resource2Response = manipulatingClient.createExpectingSuccess("ExistingResource2");

        List<String> createdIds = Lists.transform(Lists.newArrayList(resource1Response, resource2Response), JsonHelpers.ID_OF_RESOURCE);

        String subscribeResponse = subscribingClient.subscribeExpectingSuccess();

        Set<String> idsFromResources = getIdsFromSubscriptionSuccess(subscribeResponse);

        Assert.assertTrue(idsFromResources.containsAll(createdIds));
    }


    @Test
    public void newResourcesSentThrough() throws Exception
    {

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
    public void updatedResourcesSentThrough() throws Exception
    {
        String createdResponse = manipulatingClient.createExpectingSuccess("ResourceToBeChanged");
        String resourceId = JsonHelpers.ID_OF_RESOURCE.apply(createdResponse);

        subscribingClient.subscribeExpectingSuccess();

        String subscriptionsClientId = subscribingClient.getLastClientId();

        subscribingClient.expectMessages(1);

        manipulatingClient.updateExpectingSuccess(resourceId, "ChangedMatchingResource");

        String updatedMessage = subscribingClient.assertMessagesReceived("Did not receive updated message").get(0);

        assertContentOfUpdatedMessage(new JsonParser().parse(updatedMessage).getAsJsonObject(), resourceId, subscriptionsClientId);
    }



    private void assertContentOfUpdatedMessage(JsonObject updatedMessage, String matchingResourceId, String clientId)
    {
        Assert.assertEquals("updated", updatedMessage.get("message-type").getAsString());
        Assert.assertEquals(clientId, updatedMessage.get("client-id").getAsString());
        Assert.assertEquals(matchingResourceId, updatedMessage.get("resource-id").getAsString());

        JsonObject resource =  updatedMessage.get("resource").getAsJsonObject();
        Long id = resource.get("id").getAsLong();
        Assert.assertEquals(matchingResourceId, id);
        String name = resource.get("name").getAsString();
        Assert.assertEquals("ChangedMatchingResource", name);
    }



    @Test
    public void notificationOfDeleteSentThrough() throws Exception
    {

        //TODO
    }
}
