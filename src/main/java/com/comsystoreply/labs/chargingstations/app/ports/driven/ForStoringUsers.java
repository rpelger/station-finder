package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.User;

public interface ForStoringUsers {
    User createNew(String email, String password);

    User get(String email, String password);
}
