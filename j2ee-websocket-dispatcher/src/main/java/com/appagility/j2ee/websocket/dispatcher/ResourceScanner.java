package com.appagility.j2ee.websocket.dispatcher;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.List;
import java.util.Set;

public class ResourceScanner
{
    private String basePath;

    public ResourceScanner(String basePath) {

        this.basePath = basePath;
    }

    public Set<Class<?>> findResourceClasses() {

        final Reflections reflections = new Reflections(basePath, new TypeAnnotationsScanner());
        return reflections.getTypesAnnotatedWith(WebSocketResource.class);
    }
}
