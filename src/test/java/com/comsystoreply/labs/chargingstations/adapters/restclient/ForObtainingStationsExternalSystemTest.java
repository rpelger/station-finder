package com.comsystoreply.labs.chargingstations.adapters.restclient;

import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ForObtainingStationsExternalSystemTest {

    @Test
    void should_load_bna_csv_file_with_stations() {
        ForObtainingStations stationLoader = new BnaCsvStationsRestClient();

        var result = stationLoader.fetchCurrentStations();

        assertThat(result, is(not(nullValue())));
        assertThat(result, hasSize(greaterThan(0)));
        result.forEach(c -> assertThat(c.chargers(), hasSize(greaterThan(0))));
    }

}