package com.vinyl.client.view;

import com.vinyl.client.core.ViewHandler;
import com.vinyl.client.core.ViewModelFactory;

public interface ViewController
{
    void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory);
} 