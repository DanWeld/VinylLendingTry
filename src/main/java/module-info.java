module com.vinyl {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    opens com.vinyl.client to javafx.fxml;
    opens com.vinyl.client.view to javafx.fxml;
    opens com.vinyl.client.viewmodel to javafx.fxml;
    
    exports com.vinyl.client;
    exports com.vinyl.client.view;
    exports com.vinyl.client.viewmodel;
    exports com.vinyl.client.network;
    exports com.vinyl.server;
    exports com.vinyl.server.network;
    exports com.vinyl.server.model;
    exports com.vinyl.shared;
    exports com.vinyl.util;
} 