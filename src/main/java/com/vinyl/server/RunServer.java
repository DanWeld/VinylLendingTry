package com.vinyl.server;

import com.vinyl.server.model.ServerVinylModelManager;
import com.vinyl.server.network.SocketServer;

public class RunServer {
    public static void main(String[] args) {
        ServerVinylModelManager model = new ServerVinylModelManager();
        SocketServer server = new SocketServer(model);
        server.startServer();
    }
} 