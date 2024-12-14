package com.comsystoreply.labs.chargingstations.adapters.job;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForMaintainingStations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class ImportStationsJobTest {
    private StationFinderApp app;

    @BeforeEach
    void setup() {
        app =  new StationFinderApp(
                () -> List.of(
                        new Station(
                                new StationId("a"),
                                "op-1",
                                new Location(
                                        new Geo(1d, 2d),
                                        new Address("s", "h", "z", "c", "p", "s")),
                                "01.01.2024",
                                List.of(new Charger("AC-1", BigDecimal.TEN)))),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );
    }

    @Test
    void should_run_scheduled_job_immediately() throws InterruptedException {
        var job = new ImportStationsJob(app, User.SYSTEM_USER);
        assertThat(app.listAll(User.ADMIN_USER), hasSize(0));

        job.runOnceImmediately();
        Thread.sleep(100L); // requires sleeping, as job runs asynchronously

        assertThat(app.listAll(User.ADMIN_USER), hasSize(1));
    }
}