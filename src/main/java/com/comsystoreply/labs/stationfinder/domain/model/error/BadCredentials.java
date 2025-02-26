package com.comsystoreply.labs.stationfinder.domain.model.error;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.usecases.*;

public class BadCredentials extends RuntimeException {
    public BadCredentials(UserCredentials credentials) {
        super(String.format(
                "Error in %s: Failed to authenticate User(email=%s)",
                AuthenticateUser.class,
                credentials.email()));
    }
}
