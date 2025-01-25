package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.*;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.error.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Named.*;
import static org.junit.jupiter.params.provider.Arguments.*;

class ApplicationPermissionsTest {

    public static final InMemoryStationRepository STATION_REPO = new InMemoryStationRepository();
    public static final StationId STATION_ID = new StationId("1");

    private static StationFinderApp app;

    private static Stream<Arguments> usecases() {
        var findNearestStations = named("FindNearestStations", (Consumer<User>) (user) -> app.findStationsInAreaPaged(user, new Area(new Geo(0d, 0d), new Radius(0d)), new StationPageRequest()));
        var viewStationDetails = named("ViewStationDetails", (Consumer<User>) (user) -> app.viewStationDetails(user, STATION_ID));
        var listStationReviews = named("ListStationReviews", (Consumer<User>) (user) -> app.listStationReviews(user, STATION_ID));
        var addStationReview = named("AddStationReview", (Consumer<User>) (user) -> app.addStationReview(user, STATION_ID, "review1"));
        var importStations = named("ImportStations", (Consumer<User>) (user) -> app.importCurrentStations(user));
        var updateStationOperator = named("UpdateStationOperator", (Consumer<User>) (user) -> app.updateStationOperator(user, STATION_ID, "op-neu"));
        var listAllStations = named("ListAllStations", (Consumer<User>) (user) -> app.listAllStationsPaged(user, new StationPageRequest()));
        var viewStationAsAdmin = named("viewStationAsAdmin", (Consumer<User>) (user) -> app.viewStationAsAdmin(user, STATION_ID));

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
                arguments(anAdmin, isAllowedTo, viewStationAsAdmin),
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
                () -> List.of(new Station(
                        STATION_ID,
                        "op1",
                        new Location(new Geo(0d, 0d), new Address("", "", "", "", "", "")),
                        "today",
                        List.of())),
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