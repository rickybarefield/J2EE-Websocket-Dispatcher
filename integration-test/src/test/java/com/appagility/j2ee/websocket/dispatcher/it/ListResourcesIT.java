package com.appagility.j2ee.websocket.dispatcher.it;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ListResourcesIT extends TestClientEndpoint
{

    @Test
    public void allResourcesAreListed() throws Exception
    {

        expectMessages(1);
        sendMessage("{operation:'list-resources'}");
        List<String> responses = assertMessagesReceived("Did not get a response to resource listing request");

        Assert.assertTrue("The Item resource was not returned", responses.get(0).contains("Item"));
    }

}
