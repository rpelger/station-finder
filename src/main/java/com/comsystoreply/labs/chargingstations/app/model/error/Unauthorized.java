package com.comsystoreply.labs.chargingstations.app.model.error;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.User;

public class Unauthorized extends RuntimeException {
    public Unauthorized(UseCase usecase, User user) {
        super(String.format(
                "Error in %s: Unauthorized access of User(id=%s)",
                usecase.getClass(),
                user.id().value()));
    }
}
