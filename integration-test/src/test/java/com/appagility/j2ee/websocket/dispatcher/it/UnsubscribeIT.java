package com.appagility.j2ee.websocket.dispatcher.it;


import org.junit.Test;

public class UnsubscribeIT extends SubscribeUnsubscribeBase
{
    @Test
    public void testUnsubscribe() throws Exception {

        subscribingClient.subscribeExpectingSuccess();

        subscribingClient.expectMessages(1);

//        createWithManipulator("testUnsubscribe");

        subscribingClient.assertMessagesReceived("No message received when subscribed");

        //TODO
//        String unsubscribeResponse = subscribingClient.unsubscribe();
//        JsonHelpers.assertSuccess(unsubscribeResponse);
//
//        subscribingClient.expectMessages(0);
//
//        createWithManipulator("CreatedWhenUnsubscribed");
//
//        subscribingClient.assertNoMessagesReceived("Received a message when unsubscribed");
    }

}
