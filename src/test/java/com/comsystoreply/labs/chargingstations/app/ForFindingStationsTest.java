package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForFindingStations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ForFindingStationsTest {

    private static final Location LOCATION = new Location(
            new Geo(1.0d, 2.0d),
            new Address("a-street", "1", "12345", "X-Town", "NRW", "DE"));

    private ForFindingStations port;

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
        var user = User.CONSUMER_USER;
        var radius = new Radius(10.0d);
        var geo = new Geo(20.0d, 40.0d);
        var area = new Area(geo, radius);
        var pageRequest = new StationPageRequest(1, Integer.MAX_VALUE);

        var stations = port.findStationsInAreaPaged(user, area, pageRequest);

        assertThat(stations.items(), hasSize(greaterThan(0)));
    }

    @Test
    void should_view_station_details() {
        var user = User.CONSUMER_USER;
        var area = new Area(new Geo(20.0d, 40.0d), new Radius(10.0d));
        var pageRequest = new StationPageRequest(1, Integer.MAX_VALUE);

        var station = port.findStationsInAreaPaged(user, area, pageRequest).items().getFirst();
        var stationDetails = port.viewStationDetails(user, station.id());

        assertThat(station, is(equalTo(stationDetails)));
    }
}
