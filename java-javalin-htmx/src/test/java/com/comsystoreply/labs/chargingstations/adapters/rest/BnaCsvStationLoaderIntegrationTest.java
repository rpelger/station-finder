package com.comsystoreply.labs.chargingstations.adapters.rest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BnaCsvStationLoaderIntegrationTest {

    @Test
    void should_load_bna_csv_file_with_stations() {
        var result = new BnaCsvStationsRestClient().fetchCurrentStations();

        assertThat(result, is(not(nullValue())));
        assertThat(result, hasSize(greaterThan(0)));
        result.forEach(c -> assertThat(c.chargers(), hasSize(greaterThan(0))));
    }

}