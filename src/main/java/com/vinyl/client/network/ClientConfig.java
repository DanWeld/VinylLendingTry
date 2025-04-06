package com.vinyl.client.network;

public class ClientConfig {
    private static String serverHost = "localhost";
    private static final int serverPort = 2910;

    public static String getServerHost() {
        return serverHost;
    }

    public static void setServerHost(String host) {
        serverHost = host;
    }

    public static int getServerPort() {
        return serverPort;
    }
} 