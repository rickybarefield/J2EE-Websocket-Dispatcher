package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.google.common.collect.Maps;

/**
 * @author rbarefield
 */
public final class TestResourceConverterFactory
{
    public static ResourceConverter create()
    {
        WebSocketResource websocketResource = (WebSocketResource) Laptop.class.getAnnotations()[0];
        Map<Class<?>, WebSocketResource> testResourceMap = new HashMap<>();
        testResourceMap.put(Laptop.class, websocketResource);
        return new ResourceConverter(testResourceMap);
    }
}
