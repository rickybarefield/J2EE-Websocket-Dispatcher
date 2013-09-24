package com.appagility.j2ee.websocket.dispatcher.it.resources;

import com.appagility.j2ee.websocket.dispatcher.WebSocketResource;
import com.appagility.j2ee.websocket.dispatcher.it.repositories.ItemRepositoryFactory;

@WebSocketResource(name = "Item", repositoryFactory = ItemRepositoryFactory.class)
public class Item
{
    private Long id;
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
