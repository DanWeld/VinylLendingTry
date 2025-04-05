package com.vinyl.server.network;

import com.vinyl.server.model.ServerVinylModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private ServerVinylModel serverVinylModel;
    private ConnectionPool pool;
    private int totalConnections;

    public SocketServer(ServerVinylModel serverVinylModel) {
        this.serverVinylModel = serverVinylModel;
        this.totalConnections = 0;
    }

    public void startServer() {
        try {
            System.out.println("Vinyl Server started...");
            ServerSocket serverSocket = new ServerSocket(2910);
            pool = new ConnectionPool();

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                ServerSocketHandler ssh = new ServerSocketHandler(socket, serverVinylModel, pool);
                pool.addConnection(ssh);
                Thread thread = new Thread(ssh);
                thread.start();

                totalConnections++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalConnections() {
        return totalConnections;
    }

    public ConnectionPool getPool() {
        return pool;
    }
} 