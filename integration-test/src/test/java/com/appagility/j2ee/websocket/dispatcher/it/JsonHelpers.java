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

    public static Function<String, Long> ID_OF_RESOURCE =
    new Function<String, Long>()
    {
        @Override
        public Long apply(String json)
        {
            return  new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("resource").get("id").getAsLong();
        }
    };

    public static Function<JsonElement, Long> ID_OF = new Function<JsonElement, Long>()
    {
        @Override
        public Long apply(JsonElement jsonElement)
        {
            return jsonElement.getAsJsonObject().get("id").getAsLong();
        }
    };
}
