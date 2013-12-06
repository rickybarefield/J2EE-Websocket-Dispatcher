package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import com.appagility.j2ee.websocket.dispatcher.Id;
import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;

/**
 * @author rbarefield
 */
@WebSocketResource(name = "laptop", repositoryFactory = LaptopFactory.class)
public class Laptop
{
    private String name;
    private String id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Id
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}

class LaptopFactory implements RepositoryFactory<Laptop>
{

    @Override
    public SubscribingRepository<?, Laptop> create() {
        return null;
    }
}
