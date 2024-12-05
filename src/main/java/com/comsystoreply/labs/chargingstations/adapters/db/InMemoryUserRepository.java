package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.error.UserAlreadyExists;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringUsers;

import java.util.*;
import java.util.function.Predicate;

public class InMemoryUserRepository implements ForStoringUsers {
    private final Map<UserId, User> usersMap;

    public InMemoryUserRepository() {
        this.usersMap = new HashMap<>();
    }

    private static Predicate<? super User> byEmailOf(UserRegistration registration) {
        return user -> user.email().equals(registration.credentials().email());
    }

    public final ForStoringUsers withDummyData() {
        usersMap.put(new UserId(1L), new User(new UserId(1L), "r.peglger@reply.de", "test1234", "Robert", "Pelger", Set.of(User.Role.ADMIN)));
        usersMap.put(new UserId(2L), new User(new UserId(2L), "pelgero@gmail.com", "test1234", "Roberto", "Pelegrini", Set.of(User.Role.CONSUMER)));
        return this;
    }

    @Override
    public User createNew(UserRegistration registration) {
        if (usersMap.values().stream().anyMatch(byEmailOf(registration))) {
            throw new UserAlreadyExists(registration);
        }

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
    public Optional<User> findBy(UserCredentials credentials) {
        return usersMap.values().stream()
                .filter(user -> user.email().equals(credentials.email())
                        && user.password().equals(credentials.password()))
                .findFirst();

    }

    private UserId next() {
        return new UserId(usersMap.keySet()
                .stream()
                .map(UserId::value)
                .max(Comparator.naturalOrder())
                .orElse(0L) + 1L);
    }
}
