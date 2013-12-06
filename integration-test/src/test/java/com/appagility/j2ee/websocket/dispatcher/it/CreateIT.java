package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CreateIT extends TestClientEndpoint
{
    private Long expectedId;

    @Before
    public void getLatestId() throws Exception {

        String subscriptionResponse = subscribeExpectingSuccess();
        unsubscribe(JsonHelpers.stringPropertyOf(subscriptionResponse, "client-id"));

        expectedId = Integer.valueOf(new JsonParser().parse(subscriptionResponse).getAsJsonObject().getAsJsonArray("resources").size()).longValue();
    }

    @Test
    public void createItem() throws Exception {

        String createSuccess = createExpectingSuccess("MyItem");

        JsonObject jsonResponse = new JsonParser().parse(createSuccess).getAsJsonObject();

        Assert.assertEquals("create-success", jsonResponse.get("message-type").getAsString());
        assertContentOfResource(jsonResponse);
        String clientId = jsonResponse.get("client-id").getAsString();
        Assert.assertEquals(getLastClientId(), clientId);
        Assert.assertEquals(expectedId.toString(), jsonResponse.get("resource-id").getAsString());
    }

    private void assertContentOfResource(JsonObject jsonResponse)
    {
        JsonObject content =  jsonResponse.get("resource").getAsJsonObject();
        Long id = content.get("id").getAsLong();
        Assert.assertEquals(expectedId, id);
        String name = content.get("name").getAsString();
        Assert.assertEquals("MyItem", name);
    }
}
