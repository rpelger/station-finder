package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;
import com.comsystoreply.labs.chargingstations.app.usecases.error.UserAlreadyExists;

public class RegisterUser {
    private final ForStoringUsers userRepo;

    public RegisterUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserRegistration registration) {
        try {
            return userRepo.createNew(registration);
        } catch (IllegalArgumentException e) {
            throw new UserAlreadyExists(registration);
        }
    }

}
