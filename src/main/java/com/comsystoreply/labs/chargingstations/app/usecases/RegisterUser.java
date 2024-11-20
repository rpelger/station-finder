package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

public class RegisterUser {
    private final ForStoringUsers userRepo;

    public RegisterUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserRegistration registration) {
        try {
            return userRepo.createNew(registration.credentials().email(), registration.credentials().password());
        } catch (IllegalArgumentException e) {
            throw new AlreadyExists(registration);
        }
    }

    public static class AlreadyExists extends RuntimeException {
        public AlreadyExists(UserRegistration registration) {
            super(String.format("User with email %s already exists", registration.credentials().email()));
        }
    }
}
