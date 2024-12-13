package com.comsystoreply.labs.chargingstations.app.model.error;

public class Unauthorized extends RuntimeException {
    public Unauthorized(Class<?> callerClass) {
        super(String.format(
                "Error in %s: Unauthorized access.",
                callerClass.getSimpleName()
        ));
    }
}
