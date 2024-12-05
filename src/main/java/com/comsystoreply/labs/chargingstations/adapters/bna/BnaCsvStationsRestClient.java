package com.comsystoreply.labs.chargingstations.adapters.bna;

import com.comsystoreply.labs.chargingstations.app.model.Station;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import io.badgod.jayreq.JayReq;

import java.util.Collections;
import java.util.List;

public class BnaCsvStationsRestClient implements ForObtainingStations {

    private static final String BNA_DATA_URL = "https://data.bundesnetzagentur.de/Bundesnetzagentur/SharedDocs/Downloads/DE/Sachgebiete/Energie/Unternehmen_Institutionen/E_Mobilitaet/Ladesaeulenregister.csv";
    // private static final String BNA_DATA_URL = "https://data.bundesnetzagentur.de/Bundesnetzagentur/SharedDocs/Downloads/DE/Sachgebiete/Energie/Unternehmen_Institutionen/E_Mobilitaet/Ladesaeulenregister_BNetzA_2024-09-01_v2.csv";
    private final BnaCsvStationParser csvStationParser;

    public BnaCsvStationsRestClient() {
        this.csvStationParser = new BnaCsvStationParser();
    }

    @Override
    public List<Station> fetchCurrentStations() {
        return JayReq.get(BNA_DATA_URL)
                .body((status, headers, body) -> csvStationParser.parse(body))
                .orElse(Collections.emptyList());

    }
}
