package client.clientView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;

public interface ViewController
{
    void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory);
} 