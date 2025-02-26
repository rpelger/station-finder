package com.comsystoreply.labs.stationfinder.adapters.job;

import com.comsystoreply.labs.stationfinder.adapters.db.*;
import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

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
        assertThat(app.listAllStationsPaged(User.ADMIN_USER, new StationPageRequest()).items(), hasSize(0));

        job.runOnceImmediately();
        Thread.sleep(100L); // requires sleeping, as job runs asynchronously

        assertThat(app.listAllStationsPaged(User.ADMIN_USER, new StationPageRequest()).items(), hasSize(1));
    }
}