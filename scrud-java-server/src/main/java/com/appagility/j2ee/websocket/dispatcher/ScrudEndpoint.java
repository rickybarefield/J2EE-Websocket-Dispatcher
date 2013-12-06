package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.subscription.Subscription;
import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.websocket.RemoteEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ScrudEndpoint
{
    RemoteEndpoint.Basic remoteEndpoint;
    private ResourceConverter resourceConverter;

    private Map<String, Subscription<?>> subscriptionsByClientId = new HashMap<>();

    public ScrudEndpoint(RemoteEndpoint.Basic remoteEndpoint, ResourceConverter resourceConverter)
    {
        this.remoteEndpoint = remoteEndpoint;
        this.resourceConverter = resourceConverter;
    }

    private void addMessageType(JsonObject json, MessageType messageType)
    {
        json.addProperty("message-type", messageType.key());
    }


    public void created(String clientSubscriptionId, Object createdResource) throws IOException
    {
        clientIdAndResource(MessageType.CREATED, clientSubscriptionId, createdResource);
    }

    public void createSuccess(String clientId, Object createdResource) throws IOException
    {
        clientIdAndResource(MessageType.CREATE_SUCCESS, clientId, createdResource);
    }

    private void clientIdAndResource(MessageType messageType, String clientId, Object resource) throws IOException
    {
        JsonObject object = new JsonObject();
        addMessageType(object, messageType);
        object.addProperty(CommonProperties.CLIENT_ID.key(), clientId);
        resourceConverter.addToJson(object, resource);

        remoteEndpoint.sendText(object.toString());
    }

    public void subscriptionSuccess(String clientSubscriptionId, Collection<Object> existingResources) throws IOException
    {
        JsonObject object = new JsonObject();
        addMessageType(object, MessageType.SUBSCRIPTION_SUCCESS);
        object.addProperty(CommonProperties.CLIENT_ID.key(), clientSubscriptionId);

        JsonArray resourcesJson = new JsonArray();
        Iterable<JsonObject> jsonObjects = Iterables.transform(existingResources, resourceConverter.toJson);
        for(JsonObject jsonObject : jsonObjects)
        {
            resourcesJson.add(jsonObject);
        }

        object.add("resources", resourcesJson);

        remoteEndpoint.sendText(object.toString());
    }

    public void connectToSubscription(Subscription subscription)
    {
        subscriptionsByClientId.put(subscription.getClientId(), subscription);
        subscription.connect(this);
    }

    public void unsubscribeFrom(String clientId)
    {
        Subscription<?> subscription = this.subscriptionsByClientId.get(clientId);
        subscription.disconnect();
    }

    public void unsubscribeFromAll()
    {
        for(Subscription<?> subscription : subscriptionsByClientId.values())
        {
            subscription.disconnect();
        }
    }
}
