package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.Optional;

public interface ForStoringUsers {
    User createNewFrom(UserRegistration userRegistration);

    Optional<User> findBy(UserCredentials credentials);
}
