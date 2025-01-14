package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.error.Unauthorized;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ApplicationPermissionsTest {

    public static final InMemoryStationRepository STATION_REPO = new InMemoryStationRepository();
    public static final StationId STATION_ID = new StationId("1");

    private static StationFinderApp app;

    private static Stream<Arguments> usecases() {
        var findNearestStations = named("FindNearestStations", (Consumer<User>) (user) -> app.findNearestStations(user, null, null));
        var viewStationDetails = named("ViewStationDetails", (Consumer<User>) (user) -> app.viewStationDetails(user, STATION_ID));
        var listStationReviews = named("ListStationReviews", (Consumer<User>) (user) -> app.listStationReviews(user, STATION_ID));
        var addStationReview = named("AddStationReview", (Consumer<User>) (user) -> app.addStationReview(user, STATION_ID, "review1"));
        var importStations = named("ImportStations", (Consumer<User>) (user) -> app.importCurrentStations(user));
        var updateStationOperator = named("UpdateStationOperator", (Consumer<User>) (user) -> app.updateStationOperator(user, STATION_ID, "op-neu"));
        var listAllStations = named("ListAllStations", (Consumer<User>) (user) -> app.getStationsPage(user, new StationPageRequest()));

        Named<User> aGuest = named("'GUEST'", User.GUEST_USER);
        Named<User> aConsumer = named("'CONSUMER'", User.CONSUMER_USER);
        Named<User> anAdmin = named("'ADMIN'", User.ADMIN_USER);
        Named<User> aSystemUser = named("'SYSTEM_USER'", User.SYSTEM_USER);

        Named<Boolean> isAllowedTo = named("allowed", true);
        Named<Boolean> isNotAllowedTo = named("_not_ allowed", false);


        return Stream.of(
                // GUEST
                arguments(aGuest, isAllowedTo, findNearestStations),
                arguments(aGuest, isNotAllowedTo, viewStationDetails),
                arguments(aGuest, isNotAllowedTo, listStationReviews),
                arguments(aGuest, isNotAllowedTo, addStationReview),
                arguments(aGuest, isNotAllowedTo, updateStationOperator),
                arguments(aGuest, isNotAllowedTo, listAllStations),
                arguments(aGuest, isNotAllowedTo, importStations),

                // CONSUMER
                arguments(aConsumer, isAllowedTo, findNearestStations),
                arguments(aConsumer, isAllowedTo, viewStationDetails),
                arguments(aConsumer, isAllowedTo, listStationReviews),
                arguments(aConsumer, isAllowedTo, addStationReview),
                arguments(aConsumer, isNotAllowedTo, updateStationOperator),
                arguments(aConsumer, isNotAllowedTo, listAllStations),
                arguments(aConsumer, isNotAllowedTo, importStations),

                // ADMIN
                arguments(anAdmin, isNotAllowedTo, findNearestStations),
                arguments(anAdmin, isNotAllowedTo, viewStationDetails),
                arguments(anAdmin, isNotAllowedTo, listStationReviews),
                arguments(anAdmin, isNotAllowedTo, addStationReview),
                arguments(anAdmin, isAllowedTo, updateStationOperator),
                arguments(anAdmin, isAllowedTo, listAllStations),
                arguments(anAdmin, isAllowedTo, importStations),

                // SYSTEM
                arguments(aSystemUser, isNotAllowedTo, findNearestStations),
                arguments(aSystemUser, isNotAllowedTo, viewStationDetails),
                arguments(aSystemUser, isNotAllowedTo, listStationReviews),
                arguments(aSystemUser, isNotAllowedTo, addStationReview),
                arguments(aSystemUser, isNotAllowedTo, updateStationOperator),
                arguments(aSystemUser, isNotAllowedTo, listAllStations),
                arguments(aSystemUser, isAllowedTo, importStations)
        );
    }

    @BeforeAll
    static void setup() {

        app = new StationFinderApp(
                () -> List.of(new Station(STATION_ID, "op1", null, "today", List.of())),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );

        app.importCurrentStations(User.SYSTEM_USER);
    }

    @ParameterizedTest(name = "A user with role {0} is {1} to {2}")
    @MethodSource("usecases")
    void should_check_permissions(User user, boolean isAllowed, Consumer<User> consumer) {
        try {
            consumer.accept(user);
            if (!isAllowed) {
                fail("Should be forbidden");
            }
        } catch (Exception e) {
            if (isAllowed) {
                fail("should be allowed", e);
            }
            assertThat(e, instanceOf(Unauthorized.class));
        }
    }
}