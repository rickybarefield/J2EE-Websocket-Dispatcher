package com.appagility.j2ee.websocket.dispatcher;

import java.util.Collection;

public interface Repository<RESOURCE_TYPE, ID_TYPE>
{
    RESOURCE_TYPE find(ID_TYPE id);

    Collection<RESOURCE_TYPE> findAll();

    RESOURCE_TYPE persist(RESOURCE_TYPE item);

    RESOURCE_TYPE update(RESOURCE_TYPE item);

    boolean delete(ID_TYPE id);
}
