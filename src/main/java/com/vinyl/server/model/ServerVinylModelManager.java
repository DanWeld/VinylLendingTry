package com.vinyl.server.model;

import com.vinyl.shared.User;
import com.vinyl.shared.Vinyl;
import com.vinyl.shared.VinylState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerVinylModelManager implements ServerVinylModel {
    private ArrayList<User> users;
    private Map<String, Vinyl> vinyls;
    private PropertyChangeSupport support;

    public ServerVinylModelManager() {
        users = new ArrayList<>();
        vinyls = new HashMap<>();
        support = new PropertyChangeSupport(this);
        loadSampleVinyls();
    }

    private void loadSampleVinyls() {
        vinyls.put("Ethiopian Jazz", new Vinyl("1", "Mulatu Astatke", "Afro Band",1970));
        vinyls.put("Tizita", new Vinyl("2", "Tilahun", "Tilahun Gessese", 1990));
        vinyls.put("Midt om natten", new Vinyl("3", "Kim Larsen", "Kim Larsen", 1970));
        vinyls.put("Country", new Vinyl("4", "Josh Turner", "J.Turner", 2000));
        vinyls.put("Violin", new Vinyl("5", "Far east", "Japan", 1950));
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                support.firePropertyChange("userLoggedIn", null, username);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean checkUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean addVinyl(Vinyl vinyl) {
        if (!vinyls.containsKey(vinyl.getTitle())) {
            vinyls.put(vinyl.getTitle(), vinyl);
            support.firePropertyChange("vinylAdded", null, vinyl.toString());
            return true;
        }
        return false;
    }

    @Override
    public Boolean borrowVinyl(String title, String borrower) {
        Vinyl vinyl = vinyls.get(title);
        if (vinyl != null && vinyl.isAvailable()) {
            vinyl.setBorrower(borrower);
            support.firePropertyChange("vinylBorrowed", null, vinyl.toString());
            return true;
        }
        return false;
    }

    @Override
    public Boolean returnVinyl(String title) {
        Vinyl vinyl = vinyls.get(title);
        if (vinyl != null && !vinyl.isAvailable()) {
            vinyl.returnVinyl();
            support.firePropertyChange("vinylReturned", null, vinyl.toString());
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getAllVinyls() {
        ArrayList<String> vinylList = new ArrayList<>();
        for (Vinyl vinyl : vinyls.values()) {
            vinylList.add(vinyl.toString());
        }
        return vinylList;
    }

    @Override
    public ArrayList<String> getMyVinyls(String username) {
        ArrayList<String> myVinyls = new ArrayList<>();
        for (Vinyl vinyl : vinyls.values()) {
            if (vinyl.getOwner().equals(username)) {
                myVinyls.add(vinyl.toString());
            }
        }
        return myVinyls;
    }

    @Override
    public ArrayList<String> getBorrowedVinyls(String username) {
        ArrayList<String> borrowedVinyls = new ArrayList<>();
        for (Vinyl vinyl : vinyls.values()) {
            if (vinyl.getBorrower() != null && vinyl.getBorrower().equals(username)) {
                borrowedVinyls.add(vinyl.toString());
            }
        }
        return borrowedVinyls;
    }

    @Override
    public ArrayList<String> getAvailableVinyls() {
        ArrayList<String> availableVinyls = new ArrayList<>();
        for (Vinyl vinyl : vinyls.values()) {
            if (vinyl.isAvailable()) {
                availableVinyls.add(vinyl.toString());
            }
        }
        return availableVinyls;
    }

    public int totalUsers() {
        return users.size();
    }


    public int totalVinyls() {
        return vinyls.size();
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if (eventName == null || eventName.equals("")) {
            addPropertyChangeListener(listener);
        } else {
            support.addPropertyChangeListener(eventName, listener);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if (eventName == null || eventName.equals("")) {
            removePropertyChangeListener(listener);
        } else {
            support.removePropertyChangeListener(eventName, listener);
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
} 