package com.appagility.j2ee.websocket.dispatcher.it;

import org.glassfish.tyrus.client.ClientManager;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

public class QuickTest
{

    @BeforeClass
    public static void startServer() {

            ClientManager client = ClientManager.createClient();
            try
            {
                client.connectToServer(TestClientEndpoint.class, new URI("ws://localhost:8080/j2ee-dispatcher-integration-test-0.0.1-SNAPSHOT/websocket"));
            }
            catch (DeploymentException | URISyntaxException e)
            {
                throw new RuntimeException(e);
            }
    }

    @Test
    public void waitForAWhile() throws InterruptedException
    {
        Thread.sleep(30000);
    }
}