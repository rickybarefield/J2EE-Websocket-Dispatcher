package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Repository;
import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class ReadExecutor extends OperationExecutor
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

        boolean hasId = jsonObject.has("id");

        if(hasId) {

            read(jsonObject, session, resourceName);

        } else {

            readAll(jsonObject, session, resourceName);
        }

    }

    private void readAll(JsonObject jsonObject, Session session, String resourceName) throws IOException
    {

        Repository<Object,Object> repository = (Repository<Object, Object>) repositoryFactoryMap.get(resourceName).create();

        Collection<Object> results = repository.findAll();

        JsonObject response = new JsonObject();
        response.addProperty("status", "success");

        JsonArray resources = new JsonArray();

        for(Object result : results) {

            resources.add(resourceConverter.toJson(result));
        }

        response.add("resources", resources);

        session.getBasicRemote().sendText(response.toString());
    }

    private void read(JsonObject jsonObject, Session session, String resourceName) throws IOException
    {
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
