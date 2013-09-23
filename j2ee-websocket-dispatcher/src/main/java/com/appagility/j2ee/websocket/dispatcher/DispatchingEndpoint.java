package com.appagility.j2ee.websocket.dispatcher;

import javax.websocket.*;
import java.io.IOException;

public class DispatchingEndpoint extends Endpoint
{
    @Override
    public void onOpen(final Session session, EndpointConfig config)
    {
        System.out.println("onOpen");

        session.addMessageHandler(new MessageHandler.Whole<String>()
        {
            @Override
            public void onMessage(String message)
            {
                System.out.println("Received message");
                try
                {
                    session.getBasicRemote().sendText(message + " Echoed");
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("onClose");
    }
}
