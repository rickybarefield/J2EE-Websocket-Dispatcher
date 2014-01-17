package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.lang.reflect.Type;

import com.appagility.j2ee.websocket.dispatcher.CommonProperties;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.messages.HasResource;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @author rbarefield
 */
public class MessageWithResourceDeserializer implements JsonDeserializer<HasResource> {

    private ResourceConverter resourceConverter;

    public MessageWithResourceDeserializer(ResourceConverter resourceConverter)
    {
        this.resourceConverter = resourceConverter;
    }

    @Override
    public HasResource deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(new ResourceExclusionStrategy()).create();
        HasResource deserialization = gson.fromJson(jsonElement, type);

        String resourceType = deserialization.getResourceType();
        deserialization.setResource(resourceConverter.fromJson(resourceType, jsonElement.getAsJsonObject().get(CommonProperties.RESOURCE.key()).getAsJsonObject()));

        return deserialization;
    }

    private static class ResourceExclusionStrategy implements ExclusionStrategy
    {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getName().equals("resource");
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

}
