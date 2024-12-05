package com.comsystoreply.labs.chargingstations.app.model.error;

import com.comsystoreply.labs.chargingstations.app.model.StationId;

public class InvalidStationId extends RuntimeException {
    public InvalidStationId(StationId stationId) {
        super(String.format("StationId(value=%s) does not reference any station.", stationId));
    }
}
