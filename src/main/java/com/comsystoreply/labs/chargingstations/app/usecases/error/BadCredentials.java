package com.comsystoreply.labs.chargingstations.app.usecases.error;

import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.usecases.AuthenticateUser;

public class BadCredentials extends RuntimeException {
    public BadCredentials(UserCredentials credentials) {
        super(String.format(
                "Error in %s: Failed to authenticate User(email=%s)",
                AuthenticateUser.class,
                credentials.email()));
    }
}
