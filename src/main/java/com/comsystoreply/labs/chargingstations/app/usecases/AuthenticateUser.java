package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;
import com.comsystoreply.labs.chargingstations.app.usecases.error.BadCredentials;

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
}
