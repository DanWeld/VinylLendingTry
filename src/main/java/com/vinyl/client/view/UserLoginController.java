package com.vinyl.client.view;

import com.vinyl.client.core.ViewHandler;
import com.vinyl.client.core.ViewModelFactory;
import com.vinyl.client.viewmodel.UserLoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserLoginController implements ViewController
{
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private TextField loginUsernameField;

    private ViewHandler viewHandler;
    private UserLoginViewModel userLoginViewModel;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler = viewHandler;
        userLoginViewModel = viewModelFactory.getUserLoginViewModel();
        errorLabel.textProperty().bind(userLoginViewModel.errorProperty());
        loginPasswordField.textProperty().bindBidirectional(userLoginViewModel.passwordProperty());
        loginUsernameField.textProperty().bindBidirectional(userLoginViewModel.usernameProperty());
    }

    @FXML
    public void onLoginButton()
    {
        Boolean value = userLoginViewModel.login();
        if (value) {
            viewHandler.openVinylWindow();
            String user = userLoginViewModel.usernameProperty().get();
            viewHandler.getVmf().getVinylWindowViewModel().setCurrentUser(user);
        } else {
            userLoginViewModel.setErrorLabel();
        }
    }

    @FXML
    public void onSignUpButton()
    {
        viewHandler.openSignUpWindow();
    }
} 