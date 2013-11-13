package com.appagility.j2ee.websocket.dispatcher.it.repositories;

import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.j2ee.websocket.dispatcher.it.resources.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ItemRepository extends SubscribingRepository<Long, Item>
{
    private AtomicLong id = new AtomicLong(0);

    public Map<Long, Item> items = new HashMap<>();



    @Override
    public Item create(Item item)
    {
        long id = this.id.getAndAdd(1);

        item.setId(id);

        items.put(id, item);

        return item;
    }

    @Override
    protected Collection<Item> readAll()
    {
        return items.values();
    }
}
