package com.appagility.j2ee.websocket.dispatcher;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class DispatchingEndpoint extends Endpoint
{
    @Override
    public void onOpen(Session session, EndpointConfig config)
    {
        System.out.println("onOpen");

        session.addMessageHandler(new MessageHandler.Whole<String>()
        {
            @Override
            public void onMessage(String message)
            {
                System.out.println("Received message");
            }
        });
    }
}
