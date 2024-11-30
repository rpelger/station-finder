package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

import java.util.*;

public class InMemoryUserRepository implements ForStoringUsers {
    private final Map<UserId, User> usersMap;

    public final ForStoringUsers withDummyData() {
        usersMap.put(new UserId(1L), new User(new UserId(1L), "r.peglger@reply.de", "test1234", "Robert", "Pelger", Set.of(User.Role.ADMIN)));
        usersMap.put(new UserId(2L), new User(new UserId(2L), "pelgero@gmail.com", "test1234", "Roberto", "Pelegrini", Set.of(User.Role.CONSUMER)));
        return this;
    }

    public InMemoryUserRepository() {
        this.usersMap = new HashMap<>();
    }

    @Override
    public User createNew(UserRegistration registration) {
        usersMap.values().stream()
                .filter(user -> user.email().equals(registration.credentials().email()))
                .findFirst()
                .ifPresent(user -> {
                    throw new IllegalArgumentException(
                            String.format(
                                    "An entry with email '%s' already exists",
                                    registration.credentials().email()));
                });
        var newUser = new User(
                next(),
                registration.credentials().email(),
                registration.credentials().password(),
                registration.firstname(),
                registration.lastname(),
                Set.of(User.Role.CONSUMER));
        usersMap.put(newUser.id(), newUser);
        return newUser;
    }

    @Override
    public User get(UserCredentials credentials) {
        return usersMap.values().stream()
                .filter(user -> user.email().equals(credentials.email())
                        && user.password().equals(credentials.password()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("User(email=%s, password=****) not found", credentials.email())));

    }

    private UserId next() {
        return new UserId(usersMap.keySet()
                .stream()
                .map(UserId::value)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1L);
    }
}
