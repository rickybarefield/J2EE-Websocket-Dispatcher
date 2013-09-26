package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Map;

public class DispatchingEndpoint extends Endpoint
{
    private WebSocketDispatcher.EndpointConfigurator endpointConfigurator;

    public DispatchingEndpoint() {

    }

    @Override
    public void onOpen(final Session session, EndpointConfig config)
    {
        System.out.println("onOpen!" + config.getClass());

        if(endpointConfigurator == null) {

            endpointConfigurator = ((WebSocketDispatcher.EndpointConfigurator) ((ServerEndpointConfig) config).getConfigurator());
        }

        session.addMessageHandler(new Handler(session, endpointConfigurator.getOperationExecutors()));

        System.out.println("Added handler");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("onClose");

        for(OperationExecutor operationExecutor : endpointConfigurator.getOperationExecutors().values())
        {
            operationExecutor.handleSessionClose(session);
        }
    }


    private class Handler implements MessageHandler.Whole<String> {

        private Session session;
        private Map<String, OperationExecutor> operationExecutorMap;

        private Handler(Session session, Map<String, OperationExecutor> operationExecutorMap) {

            this.session = session;
            this.operationExecutorMap = operationExecutorMap;
        }

        @Override
        public void onMessage(String message)
        {
            System.out.println("Message Received: " + message);

            JsonObject json = new JsonParser().parse(message).getAsJsonObject();

            String operation = json.get("operation").getAsString();

            System.out.println("Operation: " + operation);

            try
            {
                operationExecutorMap.get(operation).execute(json, session);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

    }

}
