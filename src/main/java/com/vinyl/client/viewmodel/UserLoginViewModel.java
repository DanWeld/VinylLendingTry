package com.vinyl.client.viewmodel;

import com.vinyl.client.ClientVinylModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserLoginViewModel {
    private StringProperty username;
    private StringProperty password;
    private StringProperty error;
    private ClientVinylModel clientVinylModel;

    public UserLoginViewModel(ClientVinylModel clientVinylModel) {
        this.clientVinylModel = clientVinylModel;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        error = new SimpleStringProperty();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty errorProperty() {
        return error;
    }

    public Boolean login() {
        String username = usernameProperty().get();
        String password = passwordProperty().get();
        if (username != null && !username.equals("")) {
            return clientVinylModel.login(username, password);
        } else {
            error.set("Fields cannot be empty");
        }
        return false;
    }

    public void setErrorLabel() {
        error.set("Wrong username or password.");
    }
} 