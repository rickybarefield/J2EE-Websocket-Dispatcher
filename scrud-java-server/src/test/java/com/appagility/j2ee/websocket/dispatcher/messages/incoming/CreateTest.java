package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author rbarefield
 */
public class CreateTest
{
    private static String createMessage = "{message-type: 'create', resource-type: 'laptop', client-id: 'mySubscription1', resource: {name: 'lenovo'}}";

    @Test
    public void createFromJson()
    {
        IncomingMessage incomingMessage = new IncomingMessageFactory(TestResourceConverterFactory.create()).createFromJson(createMessage);
        assertThat(incomingMessage, instanceOf(Create.class));
        assertContent((Create) incomingMessage);
    }

    private void assertContent(Create incomingMessage) {

        assertThat(incomingMessage.getClientId(), is("mySubscription1"));
        assertThat(incomingMessage.getResourceType(), is("laptop"));
        assertThat(incomingMessage.getResource(), instanceOf(Laptop.class));
        assertContent((Laptop)incomingMessage.getResource());
    }

    private void assertContent(Laptop resource) {

        assertThat(resource.getName(), is("lenovo"));
    }


}
