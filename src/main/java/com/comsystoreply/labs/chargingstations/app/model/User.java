package com.comsystoreply.labs.chargingstations.app.model;

import java.util.Set;

public record User(
        UserId id,
        String email,
        String password,
        String firstName,
        String lastName,
        Set<Role> roles) {

    public static final User DUMMY_USER = new User(
            new UserId(-1L),
            "test@example.com",
            "test",
            "John",
            "Doe",
            Set.of(User.Role.CONSUMER));


    public UserId id() {
        return id;
    }

    public enum Role {
        CONSUMER,
        ADMIN
    }

    public boolean isConsumer() {
        return roles.contains(Role.CONSUMER);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }
}
