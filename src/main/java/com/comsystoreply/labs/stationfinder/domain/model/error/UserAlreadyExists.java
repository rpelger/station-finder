package com.comsystoreply.labs.stationfinder.domain.model.error;

import com.comsystoreply.labs.stationfinder.domain.model.*;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(UserRegistration registration) {
        super(String.format(
                "Error in %s: User(email=%s) already exists",
                UserRegistration.class,
                registration.credentials().email()));
    }
}
