package com.comsystoreply.labs.chargingstations.adapters.rest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BnaCsvStationLoaderIntegrationTest {

    @Test
    void should_load_bna_csv_file_with_stations() {
        var result = new BnaCsvStationLoader().loadAllChargingStations();

        assertThat(result, is(not(nullValue())));
    }

}