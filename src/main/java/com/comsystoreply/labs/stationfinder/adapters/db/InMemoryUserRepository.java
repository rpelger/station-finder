package com.comsystoreply.labs.stationfinder.adapters.db;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

import java.util.*;
import java.util.function.*;

public class InMemoryUserRepository implements ForStoringUsers {
    private final Map<UserId, User> usersMap;

    public InMemoryUserRepository() {
        this.usersMap = new HashMap<>();
    }

    private static Predicate<? super User> byEmailOf(UserRegistration registration) {
        return user -> user.email().equals(registration.credentials().email());
    }

    @Override
    public User createNewFrom(UserRegistration registration) {
        if (usersMap.values().stream().anyMatch(byEmailOf(registration))) {
            throw new UserAlreadyExists(registration);
        }

        var newUser = new User(
                next(),
                registration.credentials().email(),
                registration.credentials().password(),
                registration.firstname(),
                registration.lastname(),
                Set.of(User.Role.REGULAR));
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
