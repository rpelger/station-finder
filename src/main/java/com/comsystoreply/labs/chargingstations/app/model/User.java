package com.comsystoreply.labs.chargingstations.app.model;

import java.util.Set;

public record User(UserId id, String email, String password, Set<Role> roles) {

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
