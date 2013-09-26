package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.Resources;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreationExecutor extends OperationExecutor
{
    private ResourceConverter resourceConverter;
    private Map<String, RepositoryFactory<?>> nameToRepositoryFactory = new HashMap<>();

    public CreationExecutor(ResourceConverter resourceConverter, Map<String, RepositoryFactory<?>> nameToRepositoryFactory) {

        this.resourceConverter = resourceConverter;
        this.nameToRepositoryFactory = nameToRepositoryFactory;
    }

    @Override
    public String getOperationName()
    {
        return "create";
    }

    @Override
    public void execute(JsonObject jsonObject, Session session) throws IOException
    {
        JsonObject resourceJson = jsonObject.getAsJsonObject("resource");
        String resourceName = jsonObject.get("type").getAsString();

        Object resource = resourceConverter.fromJson(resourceName, resourceJson);
        RepositoryFactory<Object> repositoryFactory = (RepositoryFactory<Object>) nameToRepositoryFactory.get(resourceName);
        Object persisted = repositoryFactory.create().persist(resource);

        Resources.notifyCreate(resourceName, persisted);

        JsonObject persistedJson = resourceConverter.toJson(persisted);

        JsonObject response = new JsonObject();
        response.addProperty("status", "success");
        response.add("resource", persistedJson);

        session.getBasicRemote().sendText(response.toString());
    }
}
