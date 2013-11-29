package com.appagility.j2ee.websocket.dispatcher.operation.executors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.j2ee.websocket.dispatcher.messages.incoming.Create;

public class CreationExecutor extends OperationExecutor<Create>
{
    private Map<String, RepositoryFactory<?>> nameToRepositoryFactory = new HashMap<>();

    public CreationExecutor(Map<String, RepositoryFactory<?>> nameToRepositoryFactory)
    {
        this.nameToRepositoryFactory = nameToRepositoryFactory;
    }

    @Override
    public void execute(Create message, ScrudEndpoint scrudEndpoint) throws IOException
    {
        SubscribingRepository subscribingRepository = nameToRepositoryFactory.get(message.getResourceType()).create();

        Object createdResource = subscribingRepository.create(message.getResource());

        scrudEndpoint.createSuccess(message.getClientId(), createdResource);
    }
}
