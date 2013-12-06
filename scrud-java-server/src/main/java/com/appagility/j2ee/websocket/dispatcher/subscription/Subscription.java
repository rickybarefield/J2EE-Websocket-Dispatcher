package com.appagility.j2ee.websocket.dispatcher.subscription;

import com.appagility.j2ee.websocket.dispatcher.ScrudEndpoint;
import com.appagility.j2ee.websocket.dispatcher.SubscribingRepository;
import com.appagility.j2ee.websocket.dispatcher.Unsubscribable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Subscription<ITEM>
{
    private static AtomicLong id = new AtomicLong();

    private final String serverId;
    private final String clientId;
    private Unsubscribable unsubscribable;

    private ScrudEndpoint scrudEndpoint;

    private List<ITEM> createdItemsWhenNotConnected = new ArrayList<>();

    public Subscription(String clientId, Unsubscribable unsubscribable)
    {
        this.clientId = clientId;
        this.unsubscribable = unsubscribable;
        serverId = "server" + id.incrementAndGet();
    }

    public synchronized void connect(ScrudEndpoint scrudEndpoint)
    {
        this.scrudEndpoint = scrudEndpoint;
    }

    public synchronized void disconnect()
    {
        this.scrudEndpoint = null;
        this.unsubscribable.unsubscribe(serverId);
    }

    private boolean connected()
    {
        return scrudEndpoint != null;
    }

    public synchronized void created(ITEM item) throws IOException
    {
        if(connected())
        {
            scrudEndpoint.created(clientId, item);
        }
        else
        {
            createdItemsWhenNotConnected.add(item);
        }
    }


    public String getServerId()
    {
        return serverId;
    }

    public String getClientId()
    {
        return clientId;
    }
}
