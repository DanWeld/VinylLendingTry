package client.clientViewModel;

import client.clientModel.ClientVinylModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.Vinyl;
import shared.*;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class VinylWindowViewModel
{
    private StringProperty title;
    private StringProperty artist;
    private StringProperty username;
    private ObservableList<String> allVinyls;
    private ObservableList<String> myVinyls;
    private ObservableList<String> borrowedVinyls;
    private ObservableList<String> availableVinyls;
    private ClientVinylModel clientVinylModel;
    private String currentUser;

    public VinylWindowViewModel(ClientVinylModel clientVinylModel)
    {
        this.clientVinylModel = clientVinylModel;
        title = new SimpleStringProperty();
        artist = new SimpleStringProperty();
        username = new SimpleStringProperty();
        
        allVinyls = FXCollections.observableArrayList(clientVinylModel.getAllVinyls());
        myVinyls = FXCollections.observableArrayList();
        borrowedVinyls = FXCollections.observableArrayList();
        availableVinyls = FXCollections.observableArrayList(clientVinylModel.getAvailableVinyls());

        clientVinylModel.addPropertyChangeListener("vinylAdded", this::onVinylAdded);
        clientVinylModel.addPropertyChangeListener("vinylBorrowed", this::onVinylBorrowed);
        clientVinylModel.addPropertyChangeListener("vinylReturned", this::onVinylReturned);
    }

    private void onVinylAdded(PropertyChangeEvent event)
    {
        String vinyl = (String) event.getNewValue();
        Platform.runLater(() -> {
            allVinyls.add(vinyl);
            availableVinyls.add(vinyl);
            if (vinyl.contains("Owner: " + currentUser)) {
                myVinyls.add(vinyl);
            }
        });
    }

    private void onVinylBorrowed(PropertyChangeEvent event)
    {
        String vinyl = (String) event.getNewValue();
        Platform.runLater(() -> {
            updateVinylLists(vinyl);
            if (vinyl.contains("Borrowed by: " + currentUser)) {
                borrowedVinyls.add(vinyl);
            }
        });
    }

    private void onVinylReturned(PropertyChangeEvent event)
    {
        String vinyl = (String) event.getNewValue();
        Platform.runLater(() -> {
            updateVinylLists(vinyl);
            if (vinyl.contains("Owner: " + currentUser)) {
                myVinyls.add(vinyl);
            }
        });
    }

    private void updateVinylLists(String updatedVinyl)
    {
        for (int i = 0; i < allVinyls.size(); i++) {
            String vinyl = allVinyls.get(i);
            if (vinyl.split(" by ")[0].equals(updatedVinyl.split(" by ")[0])) {
                allVinyls.set(i, updatedVinyl);
                break;
            }
        }
        refreshLists();
    }

    private void refreshLists()
    {
        if (currentUser != null) {
            myVinyls.setAll(clientVinylModel.getMyVinyls(currentUser));
            borrowedVinyls.setAll(clientVinylModel.getBorrowedVinyls(currentUser));
        }
        availableVinyls.setAll(clientVinylModel.getAvailableVinyls());
    }

    public void addVinyl()
    {
        if (title.get() != null && !title.get().isEmpty() && 
            artist.get() != null && !artist.get().isEmpty()) {
            clientVinylModel.addVinyl(title.get(), artist.get(), currentUser);
            clearFields();
        }
    }

    public void borrowVinyl(String title)
    {
        if (title != null && !title.isEmpty()) {
            clientVinylModel.borrowVinyl(title, currentUser);
        }
    }

    public void returnVinyl(String title)
    {
        if (title != null && !title.isEmpty()) {
            clientVinylModel.returnVinyl(title);
        }
    }

    private void clearFields()
    {
        title.set("");
        artist.set("");
    }

    public void setCurrentUser(String username)
    {
        this.currentUser = username;
        this.username.set(username);
        refreshLists();
    }

    public StringProperty titleProperty()
    {
        return title;
    }

    public StringProperty artistProperty()
    {
        return artist;
    }

    public StringProperty usernameProperty()
    {
        return username;
    }

    public ObservableList<String> getAllVinyls()
    {
        return allVinyls;
    }

    public ObservableList<String> getMyVinyls()
    {
        return myVinyls;
    }

    public ObservableList<String> getBorrowedVinyls()
    {
        return borrowedVinyls;
    }

    public ObservableList<String> getAvailableVinyls()
    {
        return availableVinyls;
    }
} 