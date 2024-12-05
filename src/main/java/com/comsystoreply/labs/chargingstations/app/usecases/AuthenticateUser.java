package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.model.error.BadCredentials;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

public class AuthenticateUser implements UseCase {
    private final ForStoringUsers userRepo;

    public AuthenticateUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserCredentials credentials) {
        return userRepo.findBy(credentials).orElseThrow(() -> new BadCredentials(credentials));
    }
}
