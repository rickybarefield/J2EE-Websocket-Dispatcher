package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public class SubscribeUnsubscribeBase
{
    protected TestClientEndpoint subscribingClient = new TestClientEndpoint();
    protected TestClientEndpoint manipulatingClient = new TestClientEndpoint();

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

    protected String subscribe() throws IOException, InterruptedException
    {
        return subscribingClient.operationWithTypeExpectingResponse("No response received for subscribe", "subscribe", "Item");
    }

    protected String unsubscribe() throws IOException, InterruptedException
    {
        return subscribingClient.operationWithTypeExpectingResponse("No response received for unsubscribe", "unsubscribe", "Item");
    }

    protected Long createWithManipulator(String name) throws IOException, InterruptedException
    {
        manipulatingClient.expectMessages(1);
        manipulatingClient.sendMessage("{operation: 'create', type: 'Item', resource: {name: '" + name + "'}}");

        String createResponse = manipulatingClient.assertMessagesReceived("No response to the create").get(0);

        return new JsonParser().parse(createResponse).getAsJsonObject().getAsJsonObject("resource").get("id").getAsLong();
    }

}
