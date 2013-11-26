package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreationExecutor extends OperationExecutor
{
    private ResourceConverter resourceConverter;
    private Map<String, RepositoryFactory<?>> nameToRepositoryFactory = new HashMap<>();

    public CreationExecutor(ResourceConverter resourceConverter, Map<String, RepositoryFactory<?>> nameToRepositoryFactory)
    {
        this.resourceConverter = resourceConverter;
        this.nameToRepositoryFactory = nameToRepositoryFactory;
    }

    @Override
    public String getMessageType()
    {
        return "create";
    }

    @Override
    public void execute(JsonObject jsonObject, ScrudEndpoint scrudEndpoint) throws IOException
    {
        String resourceType = jsonObject.get("resource-type").getAsString();
        String clientId = jsonObject.get("client-id").getAsString();

        SubscribingRepository subscribingRepository = nameToRepositoryFactory.get(resourceType).create();
        JsonObject resourceJson = jsonObject.getAsJsonObject("resource");

        Object resource = resourceConverter.fromJson(resourceType, resourceJson);

        Object createdResource = subscribingRepository.create(resource);

        scrudEndpoint.created(clientId, createdResource);
    }
}
