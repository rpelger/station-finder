package com.comsystoreply.labs.chargingstations.adapters.restclient;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ForMaintainingStationsTest {
    private ChargingStationsApp app;

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
    void should_import_stations() {
        var stations = app.findNearestStations(User.DUMMY_USER, LOCATION, new Radius(10d));
        assertThat(stations, hasSize(0));

        app.importCurrentStations(User.SYSTEM_USER);
        stations = app.findNearestStations(User.DUMMY_USER, LOCATION, new Radius(10d));

        assertThat(stations, hasSize(1));
    }

    @Test
    void should_update_operator() {
        app.importCurrentStations(User.SYSTEM_USER);
        var station = app.findNearestStations(User.DUMMY_USER, LOCATION, new Radius(10d)).get(0);

        assertThat(station.operator(), is("operator1"));
        app.updateStationOperator(User.SYSTEM_USER, station.id(), "operator2");

        station = app.findNearestStations(User.DUMMY_USER, LOCATION, new Radius(10d)).get(0);
        assertThat(station.operator(), is("operator2"));
    }

}
