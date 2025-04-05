package client.clientViewModel;

import client.clientModel.ClientVinylModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SignUpViewModel {
    private StringProperty error;
    private StringProperty signUpPassword;
    private StringProperty signUpUsername;
    private ClientVinylModel clientVinylModel;

    public SignUpViewModel(ClientVinylModel clientVinylModel) {
        this.clientVinylModel = clientVinylModel;
        error = new SimpleStringProperty();
        signUpPassword = new SimpleStringProperty();
        signUpUsername = new SimpleStringProperty();
    }

    public StringProperty errorProperty() {
        return error;
    }

    public StringProperty signUpPasswordProperty() {
        return signUpPassword;
    }

    public StringProperty signUpUsernameProperty() {
        return signUpUsername;
    }

    public void checkUser() {
        String user = signUpUsernameProperty().get();
        String password = signUpPasswordProperty().get();
        if (user != null && !user.equals("")) {
            Boolean response = clientVinylModel.checkUsername(user, password);
            if (response) {
                error.set("Username already exists.");
            } else {
                error.set("Signed up successfully.");
                clearSignUpFields();
            }
        } else {
            error.set("Fields cannot be empty");
        }
    }

    private void clearSignUpFields() {
        signUpUsername.set("");
        signUpPassword.set("");
    }
} 