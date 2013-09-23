package com.appagility.j2ee.websocket.dispatcher;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

public class WebsocketDispatcher implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("Context initialised adding endpoint");

        ServerContainer serverContainer = (ServerContainer) sce.getServletContext().getAttribute("javax.websocket.server.ServerContainer");

        ServerEndpointConfig endpoint = ServerEndpointConfig.Builder.create(DispatchingEndpoint.class, "/websocket").build();

        try
        {
            serverContainer.addEndpoint(endpoint);
        }
        catch (DeploymentException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        //TODO
    }
}
