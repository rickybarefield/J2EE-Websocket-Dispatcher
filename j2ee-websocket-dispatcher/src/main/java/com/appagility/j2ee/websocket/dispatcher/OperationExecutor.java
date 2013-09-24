package com.appagility.j2ee.websocket.dispatcher;

import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

public interface OperationExecutor
{

    String getOperationName();

    void execute(JsonObject jsonObject, Session session) throws IOException;

}
