package com.comsystoreply.labs.chargingstations.app.usecases.error;

import com.comsystoreply.labs.chargingstations.app.usecases.ImportChargingStations;

public class ImportStationsError extends RuntimeException {
    public ImportStationsError(Exception e) {
        super(String.format("Error in %s: Unknown error, see stacktrace", ImportChargingStations.class), e);
    }
}
