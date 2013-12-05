package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;

import javax.annotation.Nullable;

public final class JsonHelpers
{
    public static void assertSuccess(String response) {

        assertSuccess(new JsonParser().parse(response).getAsJsonObject());
    }

    public static void assertSuccess(JsonObject jsonObject) {

        String status = jsonObject.get("status").getAsString();
        Assert.assertEquals("success", status);
    }

    public static Predicate<JsonElement> hasIdOf(final Long id) {

        return new Predicate<JsonElement>()
        {
            @Override
            public boolean apply(@Nullable JsonElement jsonElement)
            {
                return jsonElement.getAsJsonObject().get("id").getAsLong() == id.longValue();
            }
        };
    }

    public static Function<String, String> ID_OF_RESOURCE =
    new Function<String, String>()
    {
        @Override
        public String apply(String json)
        {
            return  new JsonParser().parse(json).getAsJsonObject().get("resource-id").getAsString();
        }
    };

    public static Function<JsonElement, String> ID_OF = new Function<JsonElement, String>()
    {
        @Override
        public String apply(JsonElement jsonElement)
        {
            return jsonElement.getAsJsonObject().get("resource-id").getAsString();
        }
    };
}
