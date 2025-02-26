package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class AuthenticateUser implements UseCase {
    private final ForStoringUsers userRepo;

    public AuthenticateUser(ForStoringUsers userRepo) {
        this.userRepo = userRepo;
    }

    public User apply(UserCredentials credentials) {
        return userRepo.findBy(credentials).orElseThrow(() -> new BadCredentials(credentials));
    }
}
