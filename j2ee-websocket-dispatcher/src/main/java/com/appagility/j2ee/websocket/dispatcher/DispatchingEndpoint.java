package com.appagility.j2ee.websocket.dispatcher;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchingEndpoint extends Endpoint
{
    public DispatchingEndpoint() {

    }

    @Override
    public void onOpen(final Session session, EndpointConfig config)
    {
        System.out.println("onOpen!" + config.getClass());

        session.addMessageHandler(new Handler(session, ((WebSocketDispatcher.EndpointConfigurator) ((ServerEndpointConfig) config).getConfigurator()).getOperationExecutors()));

        System.out.println("Added handler");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("onClose");
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
