package com.vinyl.client.core;

import com.vinyl.client.network.Client;
import com.vinyl.client.network.SocketClient;

public class ClientFactory {
    private Client client;

    public ClientFactory() {
        client = new SocketClient();
    }

    public Client getClient() {
        return client;
    }
} 