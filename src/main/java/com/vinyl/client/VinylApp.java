package com.vinyl.client;

import com.vinyl.client.core.ClientFactory;
import com.vinyl.client.core.ModelFactory;
import com.vinyl.client.core.ViewHandler;
import com.vinyl.client.core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class VinylApp extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        ClientFactory cf = new ClientFactory();
        ModelFactory mf = new ModelFactory(cf);
        ViewModelFactory vmf = new ViewModelFactory(mf);
        ViewHandler vh = new ViewHandler(vmf);
        vh .start();
    }
} 