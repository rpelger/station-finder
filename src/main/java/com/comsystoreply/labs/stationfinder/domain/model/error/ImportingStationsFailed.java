package com.comsystoreply.labs.stationfinder.domain.model.error;

public class ImportingStationsFailed extends RuntimeException {
    public ImportingStationsFailed(Exception cause) {
        super(cause);
    }
}
