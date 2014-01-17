package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.util.Date;

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
    private Date manufactureDateTime;
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

    public Date getManufactureDateTime() {
        return manufactureDateTime;
    }

    public void setManufactureDateTime(Date manufactureDateTime) {
        this.manufactureDateTime = manufactureDateTime;
    }
}

class LaptopFactory implements RepositoryFactory<Laptop>
{

    @Override
    public SubscribingRepository<?, Laptop> create() {
        return null;
    }
}
