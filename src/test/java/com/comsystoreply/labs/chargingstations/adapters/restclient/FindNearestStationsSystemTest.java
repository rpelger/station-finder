package com.comsystoreply.labs.chargingstations.adapters.restclient;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

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
                new InMemoryUserRepository()
        );
    }

    @Test
    void should_find_nearest_stations_after_import() {
        var user = new User(new UserId(1L), Set.of(User.Role.CONSUMER));
        var radius = new Radius(10.0d);
        var location = new Location(
                new Geo(20.0d, 40.0d),
                new Address("A-Street", "49", "53173", "Bonn", "NRW", "Germany"));

        app.importCurrentStations();
        var stations = app.findNearestStations(user, location, radius);

        assertThat(stations, hasSize(greaterThan(0)));
    }
}
