package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;

public interface ForStoringUsers {
    User createNew(UserRegistration userRegistration);

    User get(UserCredentials credentials);
}
