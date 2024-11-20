package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

public class AuthenticateUser {
    private final ForStoringUsers userRepo;

    public AuthenticateUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserCredentials credentials) {
        try {
            return userRepo.get(credentials);
        } catch (IllegalArgumentException e) {
            throw new BadCredentials(credentials);
        }
    }

    public static class BadCredentials extends RuntimeException {
        public BadCredentials(UserCredentials credentials) {
            super(String.format("Failed to authenticate User(email=%s)", credentials.email()));
        }
    }
}
