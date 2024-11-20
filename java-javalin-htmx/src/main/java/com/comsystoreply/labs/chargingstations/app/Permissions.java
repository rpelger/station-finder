package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.usecases.FindNearestStations;
import com.comsystoreply.labs.chargingstations.app.usecases.ListStationReviews;
import com.comsystoreply.labs.chargingstations.app.usecases.UseCase;

import java.util.Map;
import java.util.function.Function;

public class Permissions {

    private static final Map<Class<? extends UseCase>, Function<User, Boolean>> ALLOWANCES = Map.of(
            FindNearestStations.class, User::isConsumer,
            ListStationReviews.class, User::isConsumer
    );

    private static final Function<User, Boolean> DENY_BY_DEFAULT = any -> false;

    public static boolean isAllowed(User user, UseCase usecase) {
        return ALLOWANCES.getOrDefault(usecase.getClass(), DENY_BY_DEFAULT).apply(user);
    }

    public static void checkAllowed(User user, UseCase useCase) {
        if(!isAllowed(user, useCase)) {
            throw new Unauthorized(user, useCase);
        }
    }

    public static class Unauthorized extends RuntimeException {
        public Unauthorized(User user, UseCase usecase) {
            super(String.format("Unauthorized access of User(id=%s) on use-case: %s", user.id().value(), usecase.getClass().getSimpleName()));
        }
    }
}
