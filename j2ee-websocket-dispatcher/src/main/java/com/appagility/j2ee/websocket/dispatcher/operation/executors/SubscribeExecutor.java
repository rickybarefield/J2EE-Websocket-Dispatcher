package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Keys;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.ResourceListener;
import com.appagility.j2ee.websocket.dispatcher.Resources;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public class SubscribeExecutor extends OperationExecutor
{
    private final ReadExecutor readExecutor;
    private ResourceConverter resourceConverter;

    public SubscribeExecutor(ReadExecutor readExecutor, ResourceConverter resourceConverter)
    {
        this.readExecutor = readExecutor;
        this.resourceConverter = resourceConverter;
    }

    @Override
    public String getOperationName()
    {
        return Keys.SUBSCRIBE.value();
    }

    @Override
    public void execute(JsonObject jsonObject, Session session) throws IOException
    {
        readExecutor.execute(jsonObject, session);

        String resourceName = jsonObject.get(Keys.RESOURCE_NAME.value()).getAsString();

        Resources.registerListener(session.getId(), resourceName, new SubscribeListener<>(session));
    }

    @Override
    public void handleSessionClose(Session session)
    {
        Resources.unregisterListeners(session.getId());
    }

    private class SubscribeListener<RESOURCE_TYPE> implements ResourceListener<RESOURCE_TYPE>
    {
        private Session session;

        public SubscribeListener(Session session)
        {
            this.session = session;
        }

        @Override
        public void notifyCreate(RESOURCE_TYPE resource)
        {
            JsonObject created = new JsonObject();
            created.addProperty(Keys.OPERATION.value(), Keys.CREATE.value());
            JsonObject jsonResource = resourceConverter.toJson(resource);
            created.add(Keys.RESOURCE.value(), jsonResource);

            try
            {
                session.getBasicRemote().sendText(created.toString());
            }
            catch (IOException e)
            {
                //TODO Think!
                System.out.println(e.getMessage());
            }
        }
    }
}
