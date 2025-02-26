package com.comsystoreply.labs.stationfinder.domain.model;

import com.comsystoreply.labs.stationfinder.domain.model.error.*;

import java.util.*;

public record User(
        UserId id,
        String email,
        String password,
        String firstName,
        String lastName,
        Set<Role> roles) {

    public static User withRequiredRole(User user, Role role) {
        if(!user.roles.contains(role)) {
            throw new Unauthorized(user, role);
        }
        return user;
    }

    public static final SystemUser SYSTEM_USER = new SystemUser(new User(
            new UserId(-1L),
            "system",
            "akljdaldj29jda9pjd9ud9apdap9du9udp92jda9jd9adhadgagsjdfhkjhf8ao9eujia",
            "Charging Stations",
            "System",
            Set.of(User.Role.SYSTEM)));
    public static final Admin ADMIN_USER = new Admin(new User(
            new UserId(1L),
            "admin@example.com",
            "test",
            "Armin",
            "Admin",
            Set.of(User.Role.ADMIN)));
    public static final RegularUser CONSUMER_USER = new RegularUser(new User(
            new UserId(2L),
            "test@example.com",
            "test",
            "John",
            "Doe",
            Set.of(User.Role.REGULAR)));

    public UserId id() {
        return id;
    }

    public enum Role {
        REGULAR,
        ADMIN,
        SYSTEM
    }

    public static class RegularUser {
        public final User user;
        public RegularUser(User user) {
            this.user = withRequiredRole(user, Role.REGULAR);
        }
    }

    public static class Admin {
        public final User user;
        public Admin(User user) {
            this.user = withRequiredRole(user, Role.ADMIN);
        }
    }

    public static class SystemUser {
        public final User user;
        public SystemUser(User user) {
            this.user = withRequiredRole(user, Role.SYSTEM);
        }
    }
}
