package com.appagility.j2ee.websocket.dispatcher;

/**
 * Created with IntelliJ IDEA.
 * User: ricky
 * Date: 24/09/13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public interface RepositoryFactory<RESOURCE_TYPE>
{
    Repository<RESOURCE_TYPE, ?> create();
}
