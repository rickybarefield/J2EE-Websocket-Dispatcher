package com.appagility.j2ee.websocket.dispatcher.it;

import javax.websocket.*;

@ClientEndpoint
public class TestClientEndpoint
{

    @OnOpen
    public void onOpen(Session session) {

        System.out.println("onOpen");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("onClose");
    }

    @OnMessage
    public String onMessage(String message, Session session) {

        System.out.println("onMessage");
        return "Hi, thanks for that";
    }
}
