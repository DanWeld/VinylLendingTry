package client.core;

import client.clientModel.ClientVinylModel;
import client.clientModel.ClientModelManager;

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