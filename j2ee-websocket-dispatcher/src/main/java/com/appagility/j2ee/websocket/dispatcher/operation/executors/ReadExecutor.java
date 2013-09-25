package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Repository;
import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

public class ReadExecutor implements OperationExecutor
{
    private final Map<String, RepositoryFactory<?>> repositoryFactoryMap;
    private final ResourceConverter resourceConverter;

    public ReadExecutor(ResourceConverter resourceConverter, Map<String, RepositoryFactory<?>> repositoryFactoryMap)
    {
        this.repositoryFactoryMap = repositoryFactoryMap;
        this.resourceConverter = resourceConverter;
    }

    @Override
    public String getOperationName()
    {
        return "read";
    }

    @Override
    public void execute(JsonObject jsonObject, Session session) throws IOException
    {
        String resourceName = jsonObject.get("type").getAsString();
        JsonPrimitive id = jsonObject.get("id").getAsJsonPrimitive();

        Repository<Object,Object> repository = (Repository<Object, Object>) repositoryFactoryMap.get(resourceName).create();
        Object read = repository.find(fromJsonPrimitive(id));

        JsonObject resourceAsJson = resourceConverter.toJson(read);

        JsonObject response = new JsonObject();
        response.addProperty("status", "success");
        response.add("resource", resourceAsJson);

        session.getBasicRemote().sendText(response.toString());
    }

    private static Object fromJsonPrimitive(JsonPrimitive jsonPrimitive) {

        if(jsonPrimitive.isNumber()) {
            return jsonPrimitive.getAsLong();
        } else if(jsonPrimitive.isString()) {
            return jsonPrimitive.getAsString();
        }

        throw new RuntimeException("Id must be a number or a string");
    }
}
