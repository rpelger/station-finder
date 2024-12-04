package com.comsystoreply.labs.chargingstations.app.ports.driven.error;

import com.comsystoreply.labs.chargingstations.app.usecases.ImportChargingStations;

public class ImportProcessingFailed extends Exception {
    public ImportProcessingFailed(Exception e) {
        super(String.format("Error in %s: Unknown error, see stacktrace", ImportChargingStations.class), e);
    }
}
