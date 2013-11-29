package com.appagility.j2ee.websocket.dispatcher;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class ResourceConverter
{
    private Map<String, Class<?>> nameToResourceClass = new HashMap<>();

    public ResourceConverter(Map<Class<?>, WebSocketResource> webSocketResourceMap) {

        for(Map.Entry<Class<?>, WebSocketResource> resource: webSocketResourceMap.entrySet()) {

            Class<?> resourceClass = resource.getKey();
            String resourceName = resource.getValue().name();

            nameToResourceClass.put(resourceName, resourceClass);
        }

    }

    public <RESOURCE_TYPE> RESOURCE_TYPE fromJson(String resourceName, JsonObject json) {

        Gson gson = new Gson();
        return (RESOURCE_TYPE) gson.fromJson(json, nameToResourceClass.get(resourceName));
    }

    public <RESOURCE_TYPE> JsonObject toJson(RESOURCE_TYPE resource) {

        Gson gson = new Gson();
        return gson.toJsonTree(resource).getAsJsonObject();
    }

    public Function<Object, JsonObject> toJson = new Function<Object, JsonObject>() {

        @Override
        public JsonObject apply(Object resource)
        {
           return toJson(resource);
        }
    };
}
