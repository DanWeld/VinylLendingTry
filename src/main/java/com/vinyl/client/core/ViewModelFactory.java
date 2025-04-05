package com.vinyl.client.core;

import com.vinyl.client.viewmodel.SignUpViewModel;
import com.vinyl.client.viewmodel.UserLoginViewModel;
import com.vinyl.client.viewmodel.VinylWindowViewModel;

public class ViewModelFactory {
    private ModelFactory modelFactory;
    private SignUpViewModel signUpViewModel;
    private UserLoginViewModel userLoginViewModel;
    private VinylWindowViewModel vinylWindowViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
        signUpViewModel = new SignUpViewModel(modelFactory.getClientVinylModel());
        userLoginViewModel = new UserLoginViewModel(modelFactory.getClientVinylModel());
        vinylWindowViewModel = new VinylWindowViewModel(modelFactory.getClientVinylModel());
    }

    public SignUpViewModel getSignUpViewModel() {
        return signUpViewModel;
    }

    public UserLoginViewModel getUserLoginViewModel() {
        return userLoginViewModel;
    }

    public VinylWindowViewModel getVinylWindowViewModel() {
        return vinylWindowViewModel;
    }
} 