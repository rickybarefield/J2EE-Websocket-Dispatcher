package com.appagility.j2ee.websocket.dispatcher;

public enum Keys
{
    OPERATION("operation"),
    RESOURCE_NAME("type"),
    RESOURCE("resource"),
    RESOURCES("resources"),

    SUBSCRIBE("subscribe"),
    READ("read"),
    CREATE("create");


    private String name;

    private Keys(String name)
    {
        this.name = name;
    }

    public String value()
    {
        return name;
    }
}
