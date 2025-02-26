package com.comsystoreply.labs.stationfinder.adapters.web;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import io.javalin.http.*;

public class UserJsonApiHandler {
    private final StationFinderApp app;

    public UserJsonApiHandler(StationFinderApp app) {
        this.app = app;
    }

    public void registerNewUser(Context context) {
        var registrationRequest = context
                .bodyValidator(UserRegistrationRequest.class)
                .getOrThrow(err -> new RuntimeException());

        var user = app.regigsterNewUser(
                new UserRegistration(
                        new UserCredentials(registrationRequest.email, registrationRequest.password),
                        registrationRequest.firstname,
                        registrationRequest.lastname
                ));

        context.json(user);
    }

    public void authenticateUser(Context context) {
        var authenticationRequest = context
                .bodyValidator(UserAuthenticationRequest.class)
                .getOrThrow(err -> new RuntimeException());

        var user = app.authenticateUser(
                new UserCredentials(
                        authenticationRequest.email,
                        authenticationRequest.password)
        );


        context.sessionAttribute("current_user", user);
        context.json(user);
    }

    private record UserRegistrationRequest(
            String email,
            String password,
            String firstname,
            String lastname) {
    }

    private record UserAuthenticationRequest(
            String email,
            String password
    ) {
    }
}
