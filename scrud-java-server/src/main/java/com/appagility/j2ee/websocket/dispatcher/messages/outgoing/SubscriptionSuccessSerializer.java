package com.appagility.j2ee.websocket.dispatcher.messages.outgoing;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author rbarefield
 */
public class SubscriptionSuccessSerializer implements JsonSerializer<SubscriptionSuccess> {

    @Override
    public JsonElement serialize(SubscriptionSuccess subscriptionSuccess, Type type, JsonSerializationContext jsonSerializationContext) {

        return null;
    }
}
