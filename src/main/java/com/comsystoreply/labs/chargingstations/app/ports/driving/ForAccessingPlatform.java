package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;

public interface ForAccessingPlatform {
    User regigsterNewUser(UserRegistration registration);

    User authenticateUser(UserCredentials credentials);
}
