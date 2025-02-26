package com.comsystoreply.labs.stationfinder.domain.ports.driven;

import com.comsystoreply.labs.stationfinder.domain.model.*;

import java.util.*;

public interface ForStoringUsers {
    User createNewFrom(UserRegistration userRegistration);

    Optional<User> findBy(UserCredentials credentials);
}
