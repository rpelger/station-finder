package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.error.DuplicateKeyConflict;

import java.util.Optional;

public interface ForStoringUsers {
    User createNew(UserRegistration userRegistration) throws DuplicateKeyConflict;

    Optional<User> findBy(UserCredentials credentials);
}
