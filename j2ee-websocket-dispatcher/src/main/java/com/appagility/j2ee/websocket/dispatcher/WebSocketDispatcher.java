package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;
import java.util.Set;

public class WebSocketDispatcher implements ServletContextListener
{

    private static final String WEBSOCKET_DISPATCHER_BASE_PATH = "websocket.dispatcher.base.path";

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("Context initialised adding endpoint");

        String basePath = sce.getServletContext().getInitParameter(WEBSOCKET_DISPATCHER_BASE_PATH);

        Set<Class<?>> resourceClasses = new ResourceScanner(basePath).findResourceClasses();


        ServerContainer serverContainer = (ServerContainer) sce.getServletContext().getAttribute("javax.websocket.server.ServerContainer");


        try
        {
            ExecutorFactory executorFactory = new ExecutorFactory(resourceClasses);
            Map<String, OperationExecutor> executorMap = executorFactory.create();

            ServerEndpointConfig endpoint = ServerEndpointConfig.Builder.create(DispatchingEndpoint.class, "/websocket").configurator(new EndpointConfigurator(executorMap)).build();

            serverContainer.addEndpoint(endpoint);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
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
