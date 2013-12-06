package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author rbarefield
 */
public class SubscribeTest
{

    private static String subscribeMessage = "{message-type: 'subscribe', resource-type: 'Laptops', client-id: 'mySubscription1'}";

    @Test
    public void createFromJson()
    {
        IncomingMessage incomingMessage = new IncomingMessageFactory(TestResourceConverterFactory.create()).createFromJson(subscribeMessage);
        assertThat(incomingMessage, instanceOf(Subscribe.class));
        assertContent((Subscribe) incomingMessage);
    }

    private void assertContent(Subscribe incomingMessage) {

        assertThat(incomingMessage.getClientId(), is("mySubscription1"));
        assertThat(incomingMessage.getResourceType(), is("Laptops"));
    }

}
