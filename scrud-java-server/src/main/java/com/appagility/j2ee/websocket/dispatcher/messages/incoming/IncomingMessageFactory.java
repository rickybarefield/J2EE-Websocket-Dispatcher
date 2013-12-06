package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import com.appagility.j2ee.websocket.dispatcher.CommonProperties;
import com.appagility.j2ee.websocket.dispatcher.MessageType;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author rbarefield
 */
public class IncomingMessageFactory {

    private ResourceConverter resourceConverter;

    public IncomingMessageFactory(ResourceConverter resourceConverter)
    {
        this.resourceConverter = resourceConverter;
    }

    public IncomingMessage createFromJson(String jsonString)
    {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();

        MessageType messageType = MessageType.forKey(json.get(CommonProperties.MESSAGE_TYPE.key()).getAsString());

        MessageWithResourceDeserializer messageWithResourceDeserializer = new MessageWithResourceDeserializer(resourceConverter);

        Gson gson = new GsonBuilder().registerTypeAdapter(Create.class, messageWithResourceDeserializer).create();
        IncomingMessage incomingMessage = (IncomingMessage) gson.fromJson(json, messageType.type());

        return incomingMessage;
    }
}
