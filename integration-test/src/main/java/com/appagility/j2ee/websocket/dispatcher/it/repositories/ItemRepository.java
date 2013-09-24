package com.appagility.j2ee.websocket.dispatcher.it.repositories;

import com.appagility.j2ee.websocket.dispatcher.Repository;
import com.appagility.j2ee.websocket.dispatcher.it.resources.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ItemRepository implements Repository<Item, Long>
{
    private AtomicLong id = new AtomicLong(0);

    public Map<Long, Item> items = new HashMap<>();

    @Override
    public Item find(Long id) {

        return items.get(id);
    }

    @Override
    public Collection<Item> findAll() {

        return items.values();
    }

    @Override
    public Item persist(Item item) {

        long id = this.id.getAndAdd(1);

        item.setId(id);

        items.put(id, item);

        return item;
    }

    @Override
    public Item update(Item item) {

        Item existingItem = items.get(item.getId());
        existingItem.setName(item.getName());

        return existingItem;
    }

    @Override
    public boolean delete(Long id) {

        Item remove = items.remove(id);
        return remove != null;
    }
}
