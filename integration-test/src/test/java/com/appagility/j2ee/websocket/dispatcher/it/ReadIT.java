package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ReadIT extends TestClientEndpoint
{
    @Test
    public void createThenRead() throws Exception {

        expectMessages(1);
        sendMessage("{operation: 'create', type: 'Item', resource: {name: 'MyItem'}}");
        List<String> createResponse = assertMessagesReceived("Did not receive create response");

        Long id = new JsonParser().parse(createResponse.get(0)).getAsJsonObject().getAsJsonObject("resource").get("id").getAsLong();

        expectMessages(1);
        sendMessage("{operation: 'read', type: 'Item', id: " + id + "}");
        List<String> readResponse = assertMessagesReceived("Did not receive read response");

        String nameRetrieved = new JsonParser().parse(readResponse.get(0)).getAsJsonObject().getAsJsonObject("resource").get("name").getAsString();

        Assert.assertEquals("MyItem", nameRetrieved);
    }


}
