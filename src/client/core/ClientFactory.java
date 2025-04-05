package client.core;

import client.clientNetwork.Client;
import client.clientNetwork.SocketClient;

public class ClientFactory {
    private Client client;

    public ClientFactory() {
        client = new SocketClient();
    }

    public Client getClient() {
        return client;
    }
} 