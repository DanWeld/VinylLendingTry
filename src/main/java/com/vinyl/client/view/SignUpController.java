package com.vinyl.client.view;

import com.vinyl.client.viewmodel.SignUpViewModel;
import com.vinyl.client.core.ViewHandler;
import com.vinyl.client.core.ViewModelFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController implements ViewController
{
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField signUpPasswordField;
    @FXML
    private TextField signUpUsernameField;

    private SignUpViewModel signUpViewModel;
    private ViewHandler viewHandler;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler = viewHandler;
        signUpViewModel = viewModelFactory.getSignUpViewModel();
        errorLabel.textProperty().bind(signUpViewModel.errorProperty());
        signUpUsernameField.textProperty().bindBidirectional(signUpViewModel.signUpUsernameProperty());
        signUpPasswordField.textProperty().bindBidirectional(signUpViewModel.signUpPasswordProperty());
    }

    @FXML
    public void onSignUpButton()
    {
        signUpViewModel.checkUser();
    }

    @FXML
    public void onGoToLoginButton()
    {
        viewHandler.openLoginWindow();
    }
} 