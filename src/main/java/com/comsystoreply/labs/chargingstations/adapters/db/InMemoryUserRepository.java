package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.UserId;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryUserRepository implements ForStoringUsers {
    private static final Map<UserId, User> USERS = new HashMap<>();

    static {
        USERS.put(new UserId(1L), new User(new UserId(1L), "r.peglger@reply.de", "test1234", Set.of(User.Role.ADMIN)));
        USERS.put(new UserId(2L), new User(new UserId(2L), "pelgero@gmail.com", "test1234", Set.of(User.Role.CONSUMER)));
    }

    @Override
    public User createNew(String email, String password) {
        USERS.values().stream()
                .filter(user -> user.email().equals(email))
                .findFirst()
                .ifPresent(user -> {
                    throw new IllegalArgumentException(String.format("An entry with email '%s' already exists", email));
                });
        var newUser = new User(next(), email, password, Set.of(User.Role.CONSUMER));
        USERS.put(newUser.id(), newUser);
        return newUser;
    }

    @Override
    public User get(String email, String password) {
        return USERS.values().stream()
                .filter(user -> user.email().equals(email) && user.password().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("User(email=%s, password=****) not found", email)));

    }

    private UserId next() {
        return new UserId(USERS.keySet()
                .stream()
                .map(UserId::value)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1L);
    }
}
