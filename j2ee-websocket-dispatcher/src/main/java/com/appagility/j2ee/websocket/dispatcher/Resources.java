package com.appagility.j2ee.websocket.dispatcher;

import java.util.HashMap;
import java.util.Map;

public final class Resources
{
    private static Map<String, Map<String, ResourceListener<?>>> listenersByResourceName = new HashMap<>();

    public static synchronized void registerListener(String sessionId, String resourceName, ResourceListener<?> listener)
    {
        Map<String, ResourceListener<?>> listenersBySessionId = getListenersFor(resourceName);
        listenersBySessionId.put(sessionId, listener);
    }

    public static synchronized void unregisterListeners(String sessionId)
    {
        for(Map<String, ResourceListener<?>> listenersBySessionId : listenersByResourceName.values())
        {
            listenersBySessionId.remove(sessionId);
        }
    }

    private static Map<String, ResourceListener<?>> getListenersFor(String resourceName)
    {
        Map<String, ResourceListener<?>> listenersBySessionId = listenersByResourceName.get(resourceName);

        if(listenersBySessionId == null) {

            listenersBySessionId = new HashMap<>();
            listenersByResourceName.put(resourceName, listenersBySessionId);
        }

        return listenersBySessionId;
    }

    public static void notifyCreate(String resourceName, Object resource)
    {
        Map<String, ResourceListener<?>> listenersBySessionId = getListenersFor(resourceName);

        for(ResourceListener listener : listenersBySessionId.values()) {

            listener.notifyCreate(resource);
        }
    }
}
