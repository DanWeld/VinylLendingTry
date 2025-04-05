package com.vinyl.client.core;

import com.vinyl.client.ClientModelManager;
import com.vinyl.client.ClientVinylModel;

public class ModelFactory {
    private ClientFactory clientFactory;
    private ClientVinylModel clientVinylModel;

    public ModelFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        clientVinylModel = new ClientModelManager(clientFactory.getClient());
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    public ClientVinylModel getClientVinylModel() {
        return clientVinylModel;
    }
} 