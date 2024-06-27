package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

public class AuthenticateUser {
    public AuthenticateUser(ForStoringUsers userRepo) {

    }

    public User apply(UserCredentials credentials) {
        return null;
    }
}
