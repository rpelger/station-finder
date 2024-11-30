package com.comsystoreply.labs.chargingstations.adapters.bna;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import com.comsystoreply.labs.chargingstations.app.usecases.error.ImportStationsError;

import java.util.List;

public class BnaCsvStationsFileReader implements ForObtainingStations {

    private final BnaCsvStationParser parser = new BnaCsvStationParser();

    @Override
    public List<ChargingStation> fetchCurrentStations() {
        try(var input = getClass().getResourceAsStream("/Ladesaeulenregister_BNetzA_2024-09-01_v2.csv")) {
            var content = new String(input.readAllBytes());
            return parser.parse(content);
        } catch (Exception e) {
            throw new ImportStationsError(e);
        }
    }
}
