package com.comsystoreply.labs.stationfinder.domain.model;

public record UserRegistration(
        UserCredentials credentials,
        String firstname,
        String lastname
) {
}
