package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;

public interface ForStoringUsers {
    User createNew(UserRegistration userRegistration);

    User get(UserCredentials credentials);
}
