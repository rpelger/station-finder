package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;

import java.util.List;

public interface ForObtainingStations {
    List<ChargingStation> fetchCurrentStations();

}
