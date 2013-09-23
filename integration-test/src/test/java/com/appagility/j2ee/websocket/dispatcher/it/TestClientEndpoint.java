package com.appagility.j2ee.websocket.dispatcher.it;

import org.glassfish.tyrus.client.ClientManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class TestClientEndpoint extends Endpoint
{
    private CountDownLatch openLatch = new CountDownLatch(1);
    private CountDownLatch messagesLatch;
    private List<String> messagesReceived = new ArrayList<>();

    private static final long TIMEOUT = 2000;
    private Session session;

    @Before
    public void connect() throws InterruptedException, URISyntaxException, DeploymentException
    {
        ClientManager client = ClientManager.createClient();
        session = client.connectToServer(this, new URI("ws://localhost:8080/j2ee-dispatcher-integration-test-0.0.1-SNAPSHOT/websocket"));
        boolean started = openLatch.await(TIMEOUT, TimeUnit.MILLISECONDS);

        if(!started) {

            throw new AssertionError("Timeout waiting to establish ws connection");
        }
    }

    @After
    public void disconnect() throws IOException
    {
        session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Test finishing"));
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {

        openLatch.countDown();

        session.addMessageHandler(new MessageHandler.Whole<String>()
        {
            @Override
            public void onMessage(String message)
            {
                synchronized (this) {
                    if(messagesLatch != null && messagesLatch.getCount() > 0) {

                        messagesReceived.add(message);
                        messagesLatch.countDown();
                    }
                }
            }
        });

        System.out.println("onOpen");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("onClose");
    }

    protected void sendMessage(String message) throws IOException
    {
        session.getBasicRemote().sendText(message);
    }

    protected List<String> assertMessagesReceived(String failureMessage) throws InterruptedException
    {
        boolean allMessagesReceived = messagesLatch.await(TIMEOUT, TimeUnit.MILLISECONDS);
        Assert.assertTrue(failureMessage, allMessagesReceived);
        return messagesReceived;
    }

    protected synchronized void expectMessages(int numberExpected) {

        messagesLatch = new CountDownLatch(numberExpected);
        messagesReceived = new ArrayList<>();
    }
}
