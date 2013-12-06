package com.appagility.j2ee.websocket.dispatcher;

import java.io.IOException;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpointConfig;

import com.appagility.j2ee.websocket.dispatcher.messages.incoming.IncomingMessageFactory;
import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DispatchingEndpoint extends Endpoint
{
    private ScrudContainerInitializer.EndpointConfigurator endpointConfigurator;

    public DispatchingEndpoint() {

    }

    @Override
    public void onOpen(final Session session, EndpointConfig config)
    {
        System.out.println("onOpen!" + config.getClass());

        if(endpointConfigurator == null)
        {
            endpointConfigurator = ((ScrudContainerInitializer.EndpointConfigurator) ((ServerEndpointConfig) config).getConfigurator());
        }


        ScrudEndpoint scrudEndpoint = new ScrudEndpoint(session.getBasicRemote(), endpointConfigurator.getExecutorFactory().resourceConverter());
        IncomingMessageFactory incomingMessageFactory = new IncomingMessageFactory(endpointConfigurator.getExecutorFactory().resourceConverter());
        session.addMessageHandler(new Handler(endpointConfigurator.getExecutorFactory(), scrudEndpoint, incomingMessageFactory));

        System.out.println("Added handler");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        //TODO
    }


    private class Handler implements MessageHandler.Whole<String> {

        private IncomingMessageFactory incomingMessageFactory;
        private ExecutorFactory executorFactory;
        private ScrudEndpoint scrudEndpoint;

        private Handler(ExecutorFactory executorFactory, ScrudEndpoint scrudEndpoint, IncomingMessageFactory incomingMessageFactory) {
            this.executorFactory = executorFactory;

            this.scrudEndpoint = scrudEndpoint;
            this.incomingMessageFactory = incomingMessageFactory;
        }

        @Override
        public void onMessage(String message)
        {
            System.out.println("Message Received: " + message);

            JsonObject json = new JsonParser().parse(message).getAsJsonObject();

            String messageType = json.get("message-type").getAsString();

            System.out.println("messageType: " + messageType);

            try
            {
                incomingMessageFactory.createFromJson(message).execute(executorFactory, scrudEndpoint);

            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

    }

}
