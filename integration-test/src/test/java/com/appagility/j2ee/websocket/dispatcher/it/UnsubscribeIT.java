package com.appagility.j2ee.websocket.dispatcher.it;


import org.junit.Test;

public class UnsubscribeIT extends SubscribeUnsubscribeBase
{
    @Test
    public void testUnsubscribe() throws Exception {

        String subscribeResponse = subscribingClient.subscribeExpectingSuccess();

        String clientId = JsonHelpers.stringPropertyOf(subscribeResponse, "client-id");

        subscribingClient.expectMessages(1);

        manipulatingClient.createExpectingSuccess("UnsubscribeCreate1");

        subscribingClient.assertMessagesReceived("No message received when subscribed");

        subscribingClient.unsubscribe(clientId);

        subscribingClient.expectMessages(0);

        manipulatingClient.createExpectingSuccess("UnsubscribeCreate2");

        subscribingClient.assertNoMessagesReceived("Message received when unsubscribed");

    }

}
