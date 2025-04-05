package com.vinyl.client;

import com.vinyl.client.network.Client;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ClientModelManager implements ClientVinylModel
{
    private Client client;
    private PropertyChangeSupport support;
    private ArrayList<String> vinyls;

    public ClientModelManager(Client client)
    {
        this.client = client;
        vinyls = new ArrayList<>();
        support = new PropertyChangeSupport(this);
        client.startClient();
        client.addPropertyChangeListener("vinylAdded", this::onVinylAdded);
        client.addPropertyChangeListener("vinylBorrowed", this::onVinylBorrowed);
        client.addPropertyChangeListener("vinylReturned", this::onVinylReturned);
    }

    private void onVinylAdded(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
    }

    private void onVinylBorrowed(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
    }

    private void onVinylReturned(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
    }

    @Override
    public Boolean login(String username, String password)
    {
        return client.login(username, password);
    }

    @Override
    public Boolean checkUsername(String user, String password)
    {
        return client.checkUser(user, password);
    }

    @Override
    public Boolean addVinyl(String title, String artist, String owner)
    {
        return client.addVinyl(title, artist, owner);
    }

    @Override
    public Boolean borrowVinyl(String title, String borrower)
    {
        return client.borrowVinyl(title, borrower);
    }

    @Override
    public Boolean returnVinyl(String title)
    {
        return client.returnVinyl(title);
    }

    @Override
    public ArrayList<String> getAllVinyls()
    {
        return client.getAllVinyls();
    }

    @Override
    public ArrayList<String> getMyVinyls(String username)
    {
        return client.getMyVinyls(username);
    }

    @Override
    public ArrayList<String> getBorrowedVinyls(String username)
    {
        return client.getBorrowedVinyls(username);
    }

    @Override
    public ArrayList<String> getAvailableVinyls()
    {
        return client.getAvailableVinyls();
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if(eventName == null || eventName.equals(""))
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if(eventName == null || eventName.equals(""))
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }
} 