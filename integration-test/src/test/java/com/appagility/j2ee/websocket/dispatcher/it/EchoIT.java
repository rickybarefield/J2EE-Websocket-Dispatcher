package com.appagility.j2ee.websocket.dispatcher.it;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EchoIT extends TestClientEndpoint
{

    @Test
    public void testEcho() throws Exception
    {

        expectMessages(1);
        sendMessage("Hello");
        List<String> messages = assertMessagesReceived("Did not receive echo response");

        Assert.assertEquals(1, messages.size());
        Assert.assertEquals("Hello Echoed", messages.get(0));
    }

}
