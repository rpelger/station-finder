package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForFindingStations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ForFindingStationsTest {

    private ForFindingStations port;

    private static final Location LOCATION = new Location(
            new Geo(1.0d, 2.0d),
            new Address("a-street", "1", "12345", "X-Town", "NRW", "DE"));

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
        // we need a different port for importing data
        app.importCurrentStations(User.SYSTEM_USER);
        // but we only need the ForFindingStations port for testing here
        port = app;
    }

    @Test
    void should_find_nearest_stations_after_import() {
        var user = User.DUMMY_USER;
        var radius = new Radius(10.0d);
        var location = new Location(
                new Geo(20.0d, 40.0d),
                new Address("A-Street", "49", "53173", "Bonn", "NRW", "Germany"));

        var stations = port.findNearestStations(user, location, radius);

        assertThat(stations, hasSize(greaterThan(0)));
    }
}
