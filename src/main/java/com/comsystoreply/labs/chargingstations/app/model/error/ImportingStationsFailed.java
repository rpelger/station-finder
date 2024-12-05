package com.comsystoreply.labs.chargingstations.app.model.error;

public class ImportingStationsFailed extends RuntimeException {
    public ImportingStationsFailed(Exception cause) {
        super(cause);
    }
}
