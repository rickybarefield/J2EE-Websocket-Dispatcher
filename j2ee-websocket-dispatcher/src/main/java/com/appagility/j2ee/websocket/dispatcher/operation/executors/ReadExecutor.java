package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.Keys;
import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
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
        //TODO
    }

    private void read(JsonObject jsonObject, Session session, String resourceName) throws IOException
    {
        //TODO
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
