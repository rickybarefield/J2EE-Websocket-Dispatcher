package com.appagility.j2ee.websocket.dispatcher;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResourceConverter
{
    public static final String ECMA_COMPATIBLE_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

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

        Gson gson = new GsonBuilder().setDateFormat(ECMA_COMPATIBLE_DATETIME_FORMAT).create();
        return (RESOURCE_TYPE) gson.fromJson(json, nameToResourceClass.get(resourceName));
    }

    public <RESOURCE_TYPE> void addToJson(JsonObject wrappingJson, RESOURCE_TYPE resource) {

        wrappingJson.add(getId(resource), toJson(resource));
    }

    public JsonObject toJson(Object resource)
    {
        Gson gson = new GsonBuilder().setDateFormat(ECMA_COMPATIBLE_DATETIME_FORMAT).create();
        return gson.toJsonTree(resource).getAsJsonObject();
    }

    public String getId(Object resource)
    {
        try
        {
            return classToIdMethod.get(resource.getClass()).invoke(resource).toString();
        }
        catch (IllegalAccessException | InvocationTargetException  e)
        {
            throw new RuntimeException("Unable to get id of resource", e);
        }
    }
}
