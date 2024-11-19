package com.comsystoreply.labs.chargingstations.adapters.rest;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.UserRepository;
import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.model.Address;
import com.comsystoreply.labs.chargingstations.app.model.Geo;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class FindNearestStationsSystemTest {

    private ChargingStationsApp app;

    @BeforeEach
    void setup() {
        app = new ChargingStationsApp(
                new BnaCsvStationsRestClient(),
                new InMemoryStationRepository(),
                new UserRepository()
        );
    }

    @Test
    void should_find_nearest_stations_after_import() {
        var user = new User();
        var radius = new Radius(10.0d);
        var location = new Location(
                new Geo(20.0d, 40.0d),
                new Address("A-Street", "49", "53173", "Bonn", "NRW", "Germany"));

        app.importCurrentStations();
        var stations = app.findNearestStations(user, location, radius);

        assertThat(stations, hasSize(greaterThan(0)));
    }
}
