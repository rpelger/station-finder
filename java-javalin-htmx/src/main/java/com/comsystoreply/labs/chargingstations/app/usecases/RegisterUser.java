package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

public class RegisterUser {
    public RegisterUser(ForStoringUsers userRepo) {

    }

    public User apply(UserRegistration registration) {
        return null;
    }
}
