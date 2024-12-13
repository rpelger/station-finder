package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.model.error.Unauthorized;
import com.comsystoreply.labs.chargingstations.app.usecases.*;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PermissionsTest {

    public static final InMemoryStationRepository STATION_REPO = new InMemoryStationRepository();

    private static Stream<Arguments> usecases() {
        var findNearestStations = named("FindNearestStations", new FindNearestStations(STATION_REPO));
        var viewStationDetails = named("ViewStationDetails", new ViewStationDetails(STATION_REPO));
        var listStationReviews = named("ListStationReviews", new ListStationReviews(STATION_REPO));
        var addStationReview = named("AddStationReview", new AddStationReview(STATION_REPO));
        var importStations = named("ImportStations", new ImportStations(List::of, STATION_REPO));
        var updateStationOperator = named("UpdateStationOperator", new UpdateStationOperator(STATION_REPO));
        var listAllStations = named("ListAllStations", new ListAllStations(STATION_REPO));

        Named<User> aConsumer = named("'CONSUMER'", User.CONSUMER_USER);
        Named<User> anAdmin = named("'ADMIN'", User.ADMIN_USER);
        Named<User> aSystemUser = named("'SYSTEM_USER'", User.SYSTEM_USER);

        Named<Boolean> isAllowedTo = named("allowed", true);
        Named<Boolean> isNotAllowedTo = named("_not_ allowed", false);

        return Stream.of(
                arguments(aConsumer, isAllowedTo, findNearestStations),
                arguments(aConsumer, isAllowedTo, viewStationDetails),
                arguments(aConsumer, isAllowedTo, listStationReviews),
                arguments(aConsumer, isAllowedTo, addStationReview),
                arguments(aConsumer, isNotAllowedTo, importStations),
                arguments(aConsumer, isNotAllowedTo, updateStationOperator),
                arguments(aConsumer, isNotAllowedTo, listAllStations),

                arguments(anAdmin, isAllowedTo, importStations),
                arguments(anAdmin, isAllowedTo, updateStationOperator),
                arguments(anAdmin, isAllowedTo, listAllStations),
                arguments(anAdmin, isNotAllowedTo, findNearestStations),
                arguments(anAdmin, isNotAllowedTo, viewStationDetails),
                arguments(anAdmin, isNotAllowedTo, listStationReviews),
                arguments(anAdmin, isNotAllowedTo, addStationReview),

                arguments(aSystemUser, isAllowedTo, importStations),
                arguments(aSystemUser, isNotAllowedTo, findNearestStations),
                arguments(aSystemUser, isNotAllowedTo, viewStationDetails),
                arguments(aSystemUser, isNotAllowedTo, listStationReviews),
                arguments(aSystemUser, isNotAllowedTo, addStationReview),
                arguments(aSystemUser, isNotAllowedTo, updateStationOperator),
                arguments(aSystemUser, isNotAllowedTo, listAllStations)
        );
    }

    @ParameterizedTest(name = "A user with role {0} is {1} to {2}")
    @MethodSource("usecases")
    void should_check_permissions(User user, boolean isAllowed, UseCase useCase) {
        try {
            Permissions.checkAllowed(user, useCase);
            if (!isAllowed) {
                fail("Should be forbidden");
            }
        } catch (Exception e) {
            if (isAllowed) {
                fail("should be allowed");
            }
            assertThat(e, instanceOf(Unauthorized.class));
        }
    }
}