package com.comsystoreply.labs.chargingstations.app.usecases.error;

import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.usecases.UseCase;

public class InvalidStationId extends RuntimeException {
    public InvalidStationId(UseCase useCase, StationId stationId) {
        super(String.format("Error in %s: ChargingStation(id=%s) does not exist", useCase, stationId));
    }
}
