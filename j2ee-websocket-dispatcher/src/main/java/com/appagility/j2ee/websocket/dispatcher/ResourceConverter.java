package com.appagility.j2ee.websocket.dispatcher;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class ResourceConverter
{
    private Map<String, Class<?>> nameToResourceClass = new HashMap<>();

    private Map<Class<?>, Method> classToIdMethod = new HashMap<>();

    public ResourceConverter(Map<Class<?>, WebSocketResource> webSocketResourceMap) {

        for(Map.Entry<Class<?>, WebSocketResource> resource: webSocketResourceMap.entrySet()) {

            Class<?> resourceClass = resource.getKey();
            String resourceName = resource.getValue().name();

            nameToResourceClass.put(resourceName, resourceClass);

            Set<Method> methodsAnnotatedWithId = ReflectionUtils.getAllMethods(resourceClass, ReflectionUtils.withAnnotation(Id.class));

            if(methodsAnnotatedWithId.size() != 1)
            {
                throw new RuntimeException("Invalid resource " + resourceClass + ", exactly one method must be annotated with " + Id.class.getName());
            }

            Method idMethod = Iterables.getOnlyElement(methodsAnnotatedWithId);

            classToIdMethod.put(resourceClass, idMethod);
        }

    }

    public <RESOURCE_TYPE> RESOURCE_TYPE fromJson(String resourceName, JsonObject json) {

        Gson gson = new Gson();
        return (RESOURCE_TYPE) gson.fromJson(json, nameToResourceClass.get(resourceName));
    }

    public <RESOURCE_TYPE> JsonObject toJson(RESOURCE_TYPE resource) {

        Gson gson = new Gson();
        JsonObject wrapper = new JsonObject();
        wrapper.add(CommonProperties.RESOURCE.key(), gson.toJsonTree(resource).getAsJsonObject());
        wrapper.add(CommonProperties.RESOURCE_ID.key(), )

        return wrapper;
    }

    public Function<Object, JsonObject> toJson = new Function<Object, JsonObject>() {

        @Override
        public JsonObject apply(Object resource)
        {
           return toJson(resource);
        }
    };
}
