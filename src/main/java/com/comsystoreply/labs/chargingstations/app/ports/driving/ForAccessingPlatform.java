package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.*;

public interface ForAccessingPlatform {
    User regigsterNewUser(UserRegistration registration);

    User authenticateUser(UserCredentials credentials);
}
