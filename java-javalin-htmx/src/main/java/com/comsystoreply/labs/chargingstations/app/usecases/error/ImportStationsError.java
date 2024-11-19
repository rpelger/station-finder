package com.comsystoreply.labs.chargingstations.app.usecases.error;

public class ImportStationsError extends RuntimeException {
    public ImportStationsError(Exception e) {
        super(e);
    }
}
