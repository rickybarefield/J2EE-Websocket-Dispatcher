package com.appagility.j2ee.websocket.dispatcher.it;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author rbarefield
 */
public class UpdateIT extends TestClientEndpoint
{
    private String resourceId;

    @Before
    public void createItem() throws IOException, InterruptedException
    {
        String creationResponse = createExpectingSuccess("ItemToBeUpdated");
        resourceId = JsonHelpers.ID_OF_RESOURCE.apply(creationResponse);
    }

    @Test
    public void updateExpectingSuccess() throws IOException, InterruptedException
    {
        String updatedSuccess = updateExpectingSuccess(resourceId, "UpdatedItem");

        JsonObject jsonResponse = new JsonParser().parse(updatedSuccess).getAsJsonObject();

        Assert.assertEquals("create-success", jsonResponse.get("message-type").getAsString());
        assertContentOfResource(jsonResponse);
        String clientId = jsonResponse.get("client-id").getAsString();
        Assert.assertEquals(getLastClientId(), clientId);
        Assert.assertEquals(resourceId, jsonResponse.get("resource-id").getAsString());

    }

    private void assertContentOfResource(JsonObject jsonResponse)
    {
        JsonObject content =  jsonResponse.get("resource").getAsJsonObject();
        Long id = content.get("id").getAsLong();
        Assert.assertEquals(resourceId, id.toString());
        String name = content.get("name").getAsString();
        Assert.assertEquals("UpdatedItem", name);

    }

}
