package com.appagility.j2ee.websocket.dispatcher.it.repositories;

import com.appagility.j2ee.websocket.dispatcher.RepositoryFactory;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.j2ee.websocket.dispatcher.it.resources.Item;

public class ItemRepositoryFactory implements RepositoryFactory<Item>
{
   ItemRepository itemRepository = new ItemRepository();

    @Override
    public ItemRepository create() {

       return itemRepository;
    }
}
