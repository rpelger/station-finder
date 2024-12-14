package com.comsystoreply.labs.chargingstations.adapters.bna;

import com.comsystoreply.labs.chargingstations.app.model.Station;
import com.comsystoreply.labs.chargingstations.app.model.error.ImportingStationsFailed;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForFetchingStations;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class BnaCsvStationsFileReader implements ForFetchingStations {

    private final BnaCsvStationParser parser = new BnaCsvStationParser();

    private final String filename;

    public BnaCsvStationsFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Station> fetchStations() {
        try (var input = getClass().getResourceAsStream(filename)) {
            var content = new String(input.readAllBytes(), StandardCharsets.ISO_8859_1);
            return parser.parse(content);
        } catch (Exception e) {
            throw new ImportingStationsFailed(e);
        }
    }
}
