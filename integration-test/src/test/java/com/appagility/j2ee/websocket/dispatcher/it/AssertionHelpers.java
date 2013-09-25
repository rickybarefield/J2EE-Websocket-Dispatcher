package com.appagility.j2ee.websocket.dispatcher.it;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;

public final class AssertionHelpers
{
    public static void assertSuccess(String response) {

        assertSuccess(new JsonParser().parse(response).getAsJsonObject());
    }

    public static void assertSuccess(JsonObject jsonObject) {

        String status = jsonObject.get("status").getAsString();
        Assert.assertEquals("success", status);
    }
}
