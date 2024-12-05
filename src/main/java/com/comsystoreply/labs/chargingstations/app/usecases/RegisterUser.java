package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

public class RegisterUser implements UseCase {
    private final ForStoringUsers userRepo;

    public RegisterUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserRegistration registration) {
        return userRepo.createNew(registration);
    }

}
