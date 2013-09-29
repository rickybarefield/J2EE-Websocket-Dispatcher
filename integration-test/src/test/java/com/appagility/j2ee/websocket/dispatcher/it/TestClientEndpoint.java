package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonObject;
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

public class TestClientEndpoint extends Endpoint
{
    protected static String OPERATION = "operation";

    private CountDownLatch openLatch = new CountDownLatch(1);
    private CountDownLatch messagesLatch;
    private List<String> messagesReceived = new ArrayList<>();

    private static final long TIMEOUT = 2000;
    private static final long NEGATIVE_ASSERTION_TIMEOUT = 1000;

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

    protected String operationWithTypeExpectingResponse(String failureMessage, String operation, String type) throws IOException, InterruptedException
    {
        expectMessages(1);
        sendMessage("{operation: '" + operation + "', type: '" + type + "'}");
        return assertMessagesReceived(failureMessage).get(0);
    }

    protected List<String> assertMessagesReceived(String failureMessage) throws InterruptedException
    {
        boolean allMessagesReceived = messagesLatch.await(TIMEOUT, TimeUnit.MILLISECONDS);
        Assert.assertTrue(failureMessage, allMessagesReceived);
        return messagesReceived;
    }

    protected synchronized void assertNoMessagesReceived(String failureMessage) throws InterruptedException {

        boolean messageReceived = messagesLatch.await(NEGATIVE_ASSERTION_TIMEOUT, TimeUnit.MILLISECONDS);
        Assert.assertFalse(failureMessage, messageReceived);
    }

    protected synchronized void expectMessages(int numberExpected) {

        int numberExpectedOrOneForZero = numberExpected == 0 ? 1 : numberExpected;
        messagesLatch = new CountDownLatch(numberExpectedOrOneForZero);
        messagesReceived = new ArrayList<>();
    }
}
