package com.vinyl.server.network;

import com.vinyl.shared.Request;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private List<ServerSocketHandler> connections;

    public ConnectionPool() {
        connections = new ArrayList<>();
    }

    public void broadcast(String type, Object arg) {
        Request request = new Request(type, arg);
        for (ServerSocketHandler ssh : connections) {
            ssh.sendMessage(request);
        }
    }

    public synchronized void addConnection(ServerSocketHandler ssh) {
        connections.add(ssh);
    }

    public synchronized void removeConnection(ServerSocketHandler handler) {
        connections.remove(handler);
    }

    public List<ServerSocketHandler> getConnections() {
        return connections;
    }
} 