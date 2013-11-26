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
}
