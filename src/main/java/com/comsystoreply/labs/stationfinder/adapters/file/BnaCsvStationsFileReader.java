package com.comsystoreply.labs.stationfinder.adapters.file;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

import java.nio.charset.*;
import java.util.*;

public class BnaCsvStationsFileReader implements ForObtainingStations {

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
