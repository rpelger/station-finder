package com.comsystoreply.labs.chargingstations.app.usecases.error;

public class ImportingStationsFailed extends RuntimeException {
    public ImportingStationsFailed(Exception cause) {
        super(cause);
    }
}
