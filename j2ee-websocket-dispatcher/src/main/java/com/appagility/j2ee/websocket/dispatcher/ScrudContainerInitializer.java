package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;
import java.util.Set;

@HandlesTypes(WebSocketResource.class)
public class ScrudContainerInitializer implements ServletContainerInitializer, ServletContextListener
{


    private ExecutorFactory executorFactory;

    @Override
    public void onStartup(Set<Class<?>> websocketResourceClasses, ServletContext servletContext) throws ServletException
    {
        try
        {
            executorFactory = new ExecutorFactory(websocketResourceClasses);
        }
        catch (IllegalAccessException | InstantiationException e)
        {
            throw new ServletException("Unable to start Scrud due to underlying exception", e);
        }
        servletContext.addListener(this);
    }

    private void createEndpoint(ServletContext servletContext)
    {

        ServerContainer serverContainer = (ServerContainer) servletContext.getAttribute("javax.websocket.server.ServerContainer");

        try
        {
            ServerEndpointConfig endpoint = ServerEndpointConfig.Builder.create(DispatchingEndpoint.class, "/websocket").configurator(new EndpointConfigurator(executorFactory)).build();
            serverContainer.addEndpoint(endpoint);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        createEndpoint(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        //TODO
    }

    public class EndpointConfigurator extends ServerEndpointConfig.Configurator {


        private ExecutorFactory executorFactory;

        public EndpointConfigurator(ExecutorFactory executorFactory)
        {
            this.executorFactory = executorFactory;
        }

        public ExecutorFactory getExecutorFactory()
        {
            return executorFactory;
        }
    }
}
