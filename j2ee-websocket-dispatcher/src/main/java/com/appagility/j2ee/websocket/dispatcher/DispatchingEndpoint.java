package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.messages.incoming.IncomingMessageFactory;
import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Map;

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

        session.addMessageHandler(new Handler(session, endpointConfigurator.getExecutorFactory()));

        System.out.println("Added handler");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        //TODO
    }


    private class Handler implements MessageHandler.Whole<String> {

        private Session session;
        private ExecutorFactory executorFactory;
        private IncomingMessageFactory incomingMessageFactory;

        private Handler(Session session, ExecutorFactory executorFactory) {

            this.session = session;
            this.executorFactory = executorFactory;
            this.incomingMessageFactory = new IncomingMessageFactory(executorFactory.resourceConverter());
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
                incomingMessageFactory.createFromJson(message).execute(executorFactory, new ScrudEndpoint(session.getBasicRemote(), executorFactory.resourceConverter()));

            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

    }

}
