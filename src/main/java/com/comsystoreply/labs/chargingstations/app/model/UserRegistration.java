package com.comsystoreply.labs.chargingstations.app.model;

public record UserRegistration(
        UserCredentials credentials,
        String firstname,
        String lastname
) {
}
