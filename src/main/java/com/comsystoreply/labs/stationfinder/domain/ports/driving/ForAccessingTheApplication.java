package com.comsystoreply.labs.stationfinder.domain.ports.driving;

import com.comsystoreply.labs.stationfinder.domain.model.*;

public interface ForAccessingTheApplication {
    User regigsterNewUser(UserRegistration registration);

    User authenticateUser(UserCredentials credentials);
}
