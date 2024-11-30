package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForMaintainingStations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ForMaintainingStationsTest {
    private ForMaintainingStations app;

    private static final Location LOCATION = new Location(
            new Geo(1.0d, 2.0d),
            new Address("a-street", "1", "12345", "X-Town", "NRW", "DE"));

    @BeforeEach
    void setup() {
        app = new ChargingStationsApp(
                () -> List.of(new ChargingStation(
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
        var stations = app.listAll(User.ADMIN_USER);
        assertThat(stations, hasSize(0));
    }

    @Test
    void should_import_stations() {
        app.importCurrentStations(User.ADMIN_USER);
        var stations = app.listAll(User.ADMIN_USER);

        assertThat(stations, hasSize(greaterThan(0)));
    }

    @Test
    void should_update_operator() {
        app.importCurrentStations(User.ADMIN_USER);
        var station = app.listAll(User.ADMIN_USER).getFirst();

        assertThat(station.operator(), is("operator1"));
        app.updateStationOperator(User.ADMIN_USER, station.id(), "operator2");

        station = app.listAll(User.ADMIN_USER).getFirst();
        assertThat(station.operator(), is("operator2"));
    }

}
