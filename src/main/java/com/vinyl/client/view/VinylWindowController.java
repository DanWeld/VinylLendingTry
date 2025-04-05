package com.vinyl.client.view;

import com.vinyl.client.viewmodel.VinylWindowViewModel;
import com.vinyl.client.core.ViewHandler;
import com.vinyl.client.core.ViewModelFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class VinylWindowController implements ViewController
{
    @FXML
    private ListView<String> allVinylsListView;
    @FXML
    private ListView<String> myVinylsListView;
    @FXML
    private ListView<String> borrowedVinylsListView;
    @FXML
    private ListView<String> availableVinylsListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private Label usernameLabel;

    private VinylWindowViewModel vinylWindowViewModel;
    private ViewHandler viewHandler;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler = viewHandler;
        vinylWindowViewModel = viewModelFactory.getVinylWindowViewModel();

        allVinylsListView.setItems(vinylWindowViewModel.getAllVinyls());
        myVinylsListView.setItems(vinylWindowViewModel.getMyVinyls());
        borrowedVinylsListView.setItems(vinylWindowViewModel.getBorrowedVinyls());
        availableVinylsListView.setItems(vinylWindowViewModel.getAvailableVinyls());

        titleField.textProperty().bindBidirectional(vinylWindowViewModel.titleProperty());
        artistField.textProperty().bindBidirectional(vinylWindowViewModel.artistProperty());
        usernameLabel.textProperty().bind(vinylWindowViewModel.usernameProperty());
    }

    @FXML
    public void onAddVinylButton()
    {
        vinylWindowViewModel.addVinyl();
    }

    @FXML
    public void onBorrowButton()
    {
        String selectedVinyl = availableVinylsListView.getSelectionModel().getSelectedItem();
        if (selectedVinyl != null) {
            String title = selectedVinyl.split(" by ")[0];
            vinylWindowViewModel.borrowVinyl(title);
        }
    }

    @FXML
    public void onReturnButton()
    {
        String selectedVinyl = borrowedVinylsListView.getSelectionModel().getSelectedItem();
        if (selectedVinyl != null) {
            String title = selectedVinyl.split(" by ")[0];
            vinylWindowViewModel.returnVinyl(title);
        }
    }

    @FXML
    public void onLogoutButton()
    {
        viewHandler.openLoginWindow();
    }
} 