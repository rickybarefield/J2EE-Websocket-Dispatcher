package com.appagility.j2ee.websocket.dispatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSocketResource
{
    String name();

    Class<? extends RepositoryFactory<?>> repositoryFactory();
}
