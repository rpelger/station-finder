package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.error.Unauthorized;
import com.comsystoreply.labs.chargingstations.app.usecases.*;

import java.util.Map;
import java.util.function.Function;

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

    public static boolean isAllowed(User user, UseCase usecase) {
        return ALLOWANCES.getOrDefault(usecase.getClass(), DENY_BY_DEFAULT).apply(user);
    }

    public static void checkAllowed(User user, UseCase useCase) {
        if (!isAllowed(user, useCase)) {
            throw new Unauthorized(useCase, user);
        }
    }
}
