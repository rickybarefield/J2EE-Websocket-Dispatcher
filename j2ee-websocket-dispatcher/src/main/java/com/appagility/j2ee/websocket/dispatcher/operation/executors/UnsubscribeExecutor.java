package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Keys;
import com.appagility.j2ee.websocket.dispatcher.Resources;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public class UnsubscribeExecutor extends OperationExecutor
{
    @Override
    public String getOperationName()
    {
        return "unsubscribe";
    }

    @Override
    public void execute(JsonObject jsonObject, Session session) throws IOException
    {
        String resourceName = jsonObject.get(Keys.RESOURCE_NAME.value()).getAsString();
        Resources.unregisterListeners(session.getId(), resourceName);

        JsonObject unsubscribed = new JsonObject();
        unsubscribed.addProperty(Keys.STATUS.value(), "success");
        session.getBasicRemote().sendText(unsubscribed.toString());
    }

    @Override
    public void handleSessionClose(Session session)
    {
        Resources.unregisterListeners(session.getId());
    }
}
