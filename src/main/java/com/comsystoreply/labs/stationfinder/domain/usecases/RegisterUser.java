package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class RegisterUser implements UseCase {
    private final ForStoringUsers userRepo;

    public RegisterUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserRegistration registration) {
        return userRepo.createNewFrom(registration);
    }

}
