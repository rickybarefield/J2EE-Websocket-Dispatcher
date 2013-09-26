package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ReadIT extends TestClientEndpoint
{

    @Test
    public void readAll() throws Exception {

        //Create two items
        expectMessages(2);
        sendMessage("{operation: 'create', type: 'Item', resource: {name: 'ReadAllItem0'}}");
        sendMessage("{operation: 'create', type: 'Item', resource: {name: 'ReadAllItem1'}}");
        List<String> readResponses = assertMessagesReceived("Did not receive create response");

        Long id0 = getId(readResponses.get(0));
        Long id1 = getId(readResponses.get(1));

        expectMessages(1);
        sendMessage("{operation: 'read', type: 'Item'}");
        List<String> readAllResponses = assertMessagesReceived("Did not get read all result");

        JsonObject response = new JsonParser().parse(readAllResponses.get(0)).getAsJsonObject();

        JsonHelpers.assertSuccess(response);

        JsonArray resourceArray = response.get("resources").getAsJsonArray();

        Assert.assertTrue("There should have been at least the two items created above returned", resourceArray.size() >= 2);

        JsonObject resource0 = (JsonObject) Iterables.find(resourceArray, JsonHelpers.hasIdOf(id0));
        JsonObject resource1 = (JsonObject) Iterables.find(resourceArray, JsonHelpers.hasIdOf(id1));


        Assert.assertEquals("ReadAllItem0", resource0.get("name").getAsString());
        Assert.assertEquals("ReadAllItem1", resource1.get("name").getAsString());
    }

    @Test
    public void createThenRead() throws Exception {

        expectMessages(1);
        sendMessage("{operation: 'create', type: 'Item', resource: {name: 'MyItem'}}");
        List<String> createResponse = assertMessagesReceived("Did not receive create response");

        Long id = getId(createResponse.get(0));

        expectMessages(1);
        sendMessage("{operation: 'read', type: 'Item', id: " + id + "}");
        List<String> readResponse = assertMessagesReceived("Did not receive read response");

        String nameRetrieved = new JsonParser().parse(readResponse.get(0)).getAsJsonObject().getAsJsonObject("resource").get("name").getAsString();

        Assert.assertEquals("MyItem", nameRetrieved);
    }

    private Long getId(String response) {

        JsonObject responseJson = new JsonParser().parse(response).getAsJsonObject();
        JsonHelpers.assertSuccess(responseJson);
        return  responseJson.getAsJsonObject("resource").get("id").getAsLong();

    }

}
