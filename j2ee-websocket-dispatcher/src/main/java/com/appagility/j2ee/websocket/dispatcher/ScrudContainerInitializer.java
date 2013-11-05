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

    private Map<String,OperationExecutor> executorMap;

    @Override
    public void onStartup(Set<Class<?>> websocketResourceClasses, ServletContext servletContext) throws ServletException
    {
        executorMap = createExecutorMap(websocketResourceClasses);
        servletContext.addListener(this);
    }

    private Map<String, OperationExecutor> createExecutorMap(Set<Class<?>> websocketResourceClasses) throws ServletException
    {
        try
        {
            ExecutorFactory executorFactory = new ExecutorFactory(websocketResourceClasses);
            return executorFactory.create();
        }
        catch (IllegalAccessException | InstantiationException e)
        {
            throw new ServletException("Unable to start Scrud due to underlying exception", e);
        }
    }

    private void createEndpoint(ServletContext servletContext)
    {

        ServerContainer serverContainer = (ServerContainer) servletContext.getAttribute("javax.websocket.server.ServerContainer");

        try
        {
            ServerEndpointConfig endpoint = ServerEndpointConfig.Builder.create(DispatchingEndpoint.class, "/websocket").configurator(new EndpointConfigurator(executorMap)).build();
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

        private Map<String, OperationExecutor> operationExecutors;

        public EndpointConfigurator(Map<String, OperationExecutor> operationExecutors) {

            this.operationExecutors = operationExecutors;
        }

        public Map<String, OperationExecutor> getOperationExecutors() {

            return operationExecutors;
        }

    }
}
