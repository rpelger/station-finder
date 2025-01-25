package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.*;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ForMaintainingStationsTest {
    private static final Location LOCATION = new Location(
            new Geo(1.0d, 2.0d),
            new Address("a-street", "1", "12345", "X-Town", "NRW", "DE"));
    private ForMaintainingStations app;

    @BeforeEach
    void setup() {
        app = new StationFinderApp(
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
    }

    @Test
    void should_have_no_stations_initially() {
        var stations = app.listAllStationsPaged(User.ADMIN_USER, new StationPageRequest());
        assertThat(stations.items(), hasSize(0));
        assertThat(stations.itemsTotal(), is(0));
    }

    @Test
    void should_import_stations() {
        app.importCurrentStations(User.ADMIN_USER);
        var stations = app.listAllStationsPaged(User.ADMIN_USER, new StationPageRequest());

        assertThat(stations.items(), hasSize(1));
        assertThat(stations.itemsTotal(), is(1));
    }

    @Test
    void should_update_operator() {
        app.importCurrentStations(User.ADMIN_USER);
        var station = app.listAllStationsPaged(User.ADMIN_USER, new StationPageRequest()).items().getFirst();

        assertThat(station.operator(), is("operator1"));
        app.updateStationOperator(User.ADMIN_USER, station.id(), "operator2");

        station = app.listAllStationsPaged(User.ADMIN_USER, new StationPageRequest()).items().getFirst();
        assertThat(station.operator(), is("operator2"));
    }

}
