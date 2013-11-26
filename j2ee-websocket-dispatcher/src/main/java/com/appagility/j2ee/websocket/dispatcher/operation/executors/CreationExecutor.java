package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
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
    public String getMessageType()
    {
        return "create";
    }

    @Override
    public void execute(JsonObject jsonObject, ScrudEndpoint scrudEndpoint) throws IOException
    {
        //TODO
    }
}
