package com.comsystoreply.labs.chargingstations.adapters.restclient;

import com.comsystoreply.labs.chargingstations.adapters.restclient.bna.BnaCsvStationParser;
import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import io.badgod.jayreq.JayReq;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toMap;

public class BnaCsvStationsRestClient implements ForObtainingStations {

    private static final String BNA_DATA_URL = "https://data.bundesnetzagentur.de/Bundesnetzagentur/SharedDocs/Downloads/DE/Sachgebiete/Energie/Unternehmen_Institutionen/E_Mobilitaet/Ladesaeulenregister.csv";
    private final BnaCsvStationParser csvStationParser;

    public BnaCsvStationsRestClient() {
        this.csvStationParser = new BnaCsvStationParser();
    }

    @Override
    public List<ChargingStation> fetchCurrentStations() {
        return JayReq.get(BNA_DATA_URL)
                .body((status, headers, body) -> csvStationParser.parse(body))
                .orElse(Collections.emptyList());

    }
}
