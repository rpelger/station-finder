package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForAccessingPlatform;

public class UserManager implements ForAccessingPlatform {
    @Override
    public User regigsterNewUser(UserRegistration registration) {
        return null;
    }

    @Override
    public User authenticateUser(UserCredentials credentials) {
        return null;
    }
}
