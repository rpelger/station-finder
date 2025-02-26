package com.comsystoreply.labs.stationfinder.domain.model.error;

import com.comsystoreply.labs.stationfinder.domain.model.*;

public class Unauthorized extends RuntimeException {
    public Unauthorized(Class<?> callerClass) {
        super(String.format(
                "Error in %s: Unauthorized access.",
                callerClass.getSimpleName()
        ));
    }

    public Unauthorized(User user, User.Role desiredRole) {
        super(String.format(
                "User(id=%s) is not eligible to act as role %s",
                user.id().value(),
                desiredRole.name()
        ));
    }
}
