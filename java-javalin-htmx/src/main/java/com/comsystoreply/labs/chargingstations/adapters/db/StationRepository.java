package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;

import java.util.Collection;

public class StationRepository implements ForStoringStations {
    @Override
    public void saveAll(Collection<ChargingStation> stations) {

    }
}
