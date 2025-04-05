package com.vinyl.client.network;

import com.vinyl.shared.Request;
import com.vinyl.shared.User;
import com.vinyl.shared.Vinyl;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient implements Client {
    private PropertyChangeSupport support;

    public SocketClient() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            Socket socket = new Socket("localhost", 2910);
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
            new Thread(() -> listenToServer(outToServer, inFromServer)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenToServer(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        try {
            outToServer.writeObject(new Request("Listener", null));
            while (true) {
                Request request = (Request) inFromServer.readObject();
                support.firePropertyChange(request.getType(), null, request.getArg());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean login(String username, String password) {
        try {
            Request response = request("login", new User(username, password));
            return (Boolean) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean checkUser(String user, String password) {
        try {
            Request response = request("checkUser", new User(user, password));
            return (Boolean) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean addVinyl(String title, String artist, String owner) {
        try {
            Request response = request("addVinyl", new Vinyl(title, artist, owner,
                1982));
            return (Boolean) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean borrowVinyl(String title, String borrower) {
        try {
            Request response = request("borrowVinyl", new String[]{title, borrower});
            return (Boolean) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean returnVinyl(String title) {
        try {
            Request response = request("returnVinyl", title);
            return (Boolean) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<String> getAllVinyls() {
        try {
            Request response = request("getAllVinyls", null);
            return (ArrayList<String>) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<String> getMyVinyls(String username) {
        try {
            Request response = request("getMyVinyls", username);
            return (ArrayList<String>) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<String> getBorrowedVinyls(String username) {
        try {
            Request response = request("getBorrowedVinyls", username);
            return (ArrayList<String>) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<String> getAvailableVinyls() {
        try {
            Request response = request("getAvailableVinyls", null);
            return (ArrayList<String>) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Request request(String type, Object arg) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 2910);
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
        outToServer.writeObject(new Request(type, arg));
        return (Request) inFromServer.readObject();
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if (eventName == null || eventName.equals(""))
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if (eventName == null || eventName.equals(""))
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
} 