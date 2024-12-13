package com.comsystoreply.labs.chargingstations.app.model;

import java.util.Set;

public record User(
        UserId id,
        String email,
        String password,
        String firstName,
        String lastName,
        Set<Role> roles) {


    public enum Role {
        CONSUMER,
        ADMIN,
        SYSTEM
    }

    public static final User SYSTEM_USER = new User(
            new UserId(-1L),
            "system",
            "akljdaldj29jda9pjd9ud9apdap9du9udp92jda9jd9adhadgagsjdfhkjhf8ao9eujia",
            "Charging Stations",
            "System",
            Set.of(User.Role.SYSTEM));
    public static final User CONSUMER_USER = new User(
            new UserId(-99L),
            "test@example.com",
            "test",
            "John",
            "Doe",
            Set.of(User.Role.CONSUMER));
    public static final User ADMIN_USER = new User(
            new UserId(1L),
            "admin@example.com",
            "test",
            "Armin",
            "Admin",
            Set.of(User.Role.ADMIN));


    public UserId id() {
        return id;
    }


    public boolean isConsumer() {
        return roles.contains(Role.CONSUMER);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isSystem() {
        return roles.contains(Role.SYSTEM);
    }
}
