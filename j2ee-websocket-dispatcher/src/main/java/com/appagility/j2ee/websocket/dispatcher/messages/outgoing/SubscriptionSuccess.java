package com.appagility.j2ee.websocket.dispatcher.messages.outgoing;

import java.util.Collection;

import com.appagility.j2ee.websocket.dispatcher.messages.HasResource;
import com.google.gson.annotations.SerializedName;

/**
 * @author rbarefield
 */
public class SubscriptionSuccess extends OutgoingMessage
{
    private Collection<Object> resources;

    public SubscriptionSuccess(Collection<Object> existingResources)
    {
        super("subscription-success");
        resources = existingResources;
    }


    private static class ResourceWithTypeAndId implements HasResource
    {

        @SerializedName("resource-type")
        private String resourceType;

        @SerializedName("resource-id")
        private String resourceId;

        @SerializedName("resource")
        private Object resource;

        @Override
        public Object getResource() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setResource(Object resource) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getResourceType() {

            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setResourceType(String resourceType) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
