package com.comsystoreply.labs.stationfinder.domain;

import com.comsystoreply.labs.stationfinder.adapters.db.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driving.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.*;
import java.util.*;

public class ForUsingTheApplicationTest {
    public static final StationId STATION_ID = new StationId("cs-001");
    private static final Location LOCATION = new Location(
            new Geo(1.0d, 2.0d),
            new Address("a-street", "1", "12345", "X-Town", "NRW", "DE"));

    private ForUsingTheApplication port;

    @BeforeEach
    void setup() {
        StationFinderApp app = new StationFinderApp(
                () -> List.of(new Station(
                        new StationId("cs-001"),
                        "operator1",
                        LOCATION,
                        "01.01.2024",
                        List.of(new Charger("AC Kupplung 1", BigDecimal.valueOf(11L)))
                )),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );
        app.importCurrentStations(User.ADMIN_USER);

        // but we only need the ForFindingStations port for testing here
        port = app;
    }

    @Test
    void should_find_nearest_stations_after_import() {
        var radius = new Radius(10.0d);
        var geo = new Geo(LOCATION.geo().lat() + 0.000005d, LOCATION.geo().lon() + 0.000005d);
        var area = new Area(geo, radius);
        var pageRequest = new StationPageRequest(1, Integer.MAX_VALUE);

        var stations = port.findStationsInAreaPaged(area, pageRequest);

        assertThat(stations.items(), hasSize(greaterThan(0)));
    }

    @Test
    void should_view_station_details() {
        var user = User.CONSUMER_USER;
        var area = new Area(LOCATION.geo(), new Radius(10.0d));
        var pageRequest = new StationPageRequest(1, Integer.MAX_VALUE);

        var station = port.findStationsInAreaPaged(area, pageRequest).items().getFirst();
        var stationDetails = port.viewStationDetails(user, station.id());

        assertThat(station, is(equalTo(stationDetails)));
    }

    @Test
    void should_list_zero_reviews_initially() {
        var reviews = port.listStationReviews(User.CONSUMER_USER, STATION_ID);
        assertThat(reviews, hasSize(0));
    }

    @Test
    void should_list_added_reviews() {
        port.addStationReview(User.CONSUMER_USER, STATION_ID, "Review 1");
        port.addStationReview(User.CONSUMER_USER, STATION_ID, "Review 2");

        var reviews = port.listStationReviews(User.CONSUMER_USER, STATION_ID);

        assertThat(reviews, hasSize(2));
        assertThat(reviews.get(0).comment(), is("Review 1"));
        assertThat(reviews.get(1).comment(), is("Review 2"));
    }

    @Test
    void should_throw_if_given_station_id_does_not_exist() {
        try {
            port.addStationReview(User.CONSUMER_USER, new StationId("not-exist"), "Review 1");
            fail("Should have thrown");
        } catch (Exception e) {
            assertThat(e, instanceOf(InvalidStationId.class));
        }
    }

    @Test
    void should_delete_existing_review_of_user() {
        var r1 = port.addStationReview(User.CONSUMER_USER, STATION_ID, "Review 1");
        var r2 = port.addStationReview(User.CONSUMER_USER, STATION_ID, "Review 2");

        assertThat(port.listStationReviews(User.CONSUMER_USER, STATION_ID), contains(r1, r2));

        port.deleteStationReview(User.CONSUMER_USER, r1.id());

        assertThat(port.listStationReviews(User.CONSUMER_USER, STATION_ID), contains(r2));
    }

    @Test
    void should_not_delete_existing_review_of_another_user() {
        var user2 = new User.RegularUser(new User(new UserId(298392L), null, null, null, null, Set.of(User.Role.REGULAR)));
        var r1 = port.addStationReview(user2, STATION_ID, "Review 1");
        var r2 = port.addStationReview(User.CONSUMER_USER, STATION_ID, "Review 2");

        assertThat(port.listStationReviews(User.CONSUMER_USER, STATION_ID), contains(r1, r2));

        try {
            port.deleteStationReview(User.CONSUMER_USER, r1.id());
            fail("Should have thrown unauthorized");
        } catch (Exception e) {
            assertThat(e, instanceOf(Unauthorized.class));
        }
    }

    @Test
    void should_not_delete_anything_for_unknown_review_id() {
        var r1 = port.addStationReview(User.CONSUMER_USER, STATION_ID, "Review 1");

        port.deleteStationReview(User.CONSUMER_USER, new ReviewId("some-other-id"));

        assertThat(port.listStationReviews(User.CONSUMER_USER, STATION_ID), contains(r1));
    }
}
