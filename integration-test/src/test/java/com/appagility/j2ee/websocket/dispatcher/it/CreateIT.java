package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CreateIT extends TestClientEndpoint
{
    //TODO This will be unknow so may have to do a getAll before this to find out what's expected
    private static Long EXPECTED_ID = 1L;

    @Test
    public void createItem() throws Exception {

        expectMessages(1);
        sendMessage("{operation:'create', type:'Item', resource:{name:'MyItem'}}");
        List<String> messages = assertMessagesReceived("No response from create");

        JsonObject jsonResponse = new JsonParser().parse(messages.get(0)).getAsJsonObject();

        Assert.assertEquals("success", jsonResponse.get("status").getAsString());

        JsonObject content =  jsonResponse.get("resource").getAsJsonObject();
        Long id = content.get("id").getAsLong();
        Assert.assertEquals(EXPECTED_ID, id);

        String name = content.get("name").getAsString();
        Assert.assertEquals("MyItem", name);
    }
}
