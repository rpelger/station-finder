package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.error.InvalidStationId;
import com.comsystoreply.labs.chargingstations.app.model.error.Unauthorized;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForReviewingStations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ForReviewingStationsTest {

    public static final StationId STATION_ID = new StationId("cs-001");

    private ForReviewingStations port;

    @BeforeEach
    void setup() {
        StationFinderApp app = new StationFinderApp(
                () -> List.of(new Station(
                        STATION_ID,
                        "operator1",
                        new Location(
                                new Geo(1.0d, 2.0d),
                                new Address("a-street", "1", "12345", "X-Town", "NRW", "DE")),
                        "01.01.2024",
                        List.of(new Charger("AC Kupplung 1", BigDecimal.valueOf(11L)))
                )),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );
        // we need a different port for importing data
        app.importCurrentStations(User.SYSTEM_USER);
        // but we only need the ForFindingStations port for testing here
        port = app;
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
        var user2 = new User(new UserId(298392L), null, null, null, null, Set.of(User.Role.CONSUMER));
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
