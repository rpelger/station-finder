package com.comsystoreply.labs.chargingstations.app.model.error;

import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(UserRegistration registration) {
        super(String.format(
                "Error in %s: User(email=%s) already exists",
                UserRegistration.class,
                registration.credentials().email()));
    }
}
