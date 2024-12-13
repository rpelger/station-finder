package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.error.Unauthorized;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Permissions {

    private static final Map<Class<? extends UseCase>, Function<User, Boolean>> ALLOWANCES = Map.of(
            FindNearestStations.class, User::isConsumer,
            ViewStationDetails.class, User::isConsumer,
            ListStationReviews.class, User::isConsumer,
            AddStationReview.class, User::isConsumer,

            UpdateStationOperator.class, User::isAdmin,
            ImportStations.class, user -> user.isAdmin() || user.isSystem(),
            ListAllStations.class, User::isAdmin
    );

    private static final Function<User, Boolean> DENY_BY_DEFAULT = any -> false;

    public static void checkAllowed(User user, UseCase useCase) {
        checkAllowed(user, useCase, () -> isAllowed(user, useCase));
    }

    public static void checkAllowed(User user, UseCase useCase, Supplier<Boolean> condition) {
        if (!condition.get()) {
            throw new Unauthorized(useCase, user);
        }
    }

    private static boolean isAllowed(User user, UseCase usecase) {
        return ALLOWANCES.getOrDefault(usecase.getClass(), DENY_BY_DEFAULT).apply(user);
    }
}
