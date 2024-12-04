package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.ports.driven.error.ImportProcessingFailed;

import java.util.List;

public interface ForObtainingStations {
    List<ChargingStation> fetchCurrentStations() throws ImportProcessingFailed;
}
