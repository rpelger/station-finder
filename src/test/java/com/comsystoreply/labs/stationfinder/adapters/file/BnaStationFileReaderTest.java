package com.comsystoreply.labs.stationfinder.adapters.file;

import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class BnaStationFileReaderTest {

    @Test
    void should_load_bna_csv_file_with_stations() {
        var stationLoader = new BnaCsvStationsFileReader("/Ladesaeulenregister_BNetzA_2024-09-01_v2.csv");
        var result = stationLoader.fetchStations();

        assertThat(result, is(not(nullValue())));
        assertThat(result, hasSize(greaterThan(0)));
        result.forEach(c -> assertThat(c.chargers(), hasSize(greaterThan(0))));
    }

    @Test
    void should_fail_when_file_not_exists() {
        var stationLoader = new BnaCsvStationsFileReader("/does-not-exist.csv");
        try {
            var test = stationLoader.fetchStations();
            fail("Should have thrown");
        } catch (Exception e) {
            assertThat(e, is(instanceOf(ImportingStationsFailed.class)));
        }
    }

}