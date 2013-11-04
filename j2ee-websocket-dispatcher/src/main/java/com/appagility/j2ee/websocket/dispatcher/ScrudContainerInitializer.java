package com.appagility.j2ee.websocket.dispatcher;

import com.appagility.j2ee.websocket.dispatcher.operation.ExecutorFactory;
import com.appagility.j2ee.websocket.dispatcher.operation.executors.OperationExecutor;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;
import java.util.Set;

@HandlesTypes(WebSocketResource.class)
public class ScrudContainerInitializer implements ServletContainerInitializer
{

    //TODO

    @Override
    public void onStartup(Set<Class<?>> websocketResourceClasses, ServletContext servletContext) throws ServletException
    {
        Map<String, OperationExecutor> executorMap = createExecutorMap(websocketResourceClasses);
        createEndpoint(servletContext, executorMap);
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

    private void createEndpoint(ServletContext servletContext, Map<String, OperationExecutor> executorMap)
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
