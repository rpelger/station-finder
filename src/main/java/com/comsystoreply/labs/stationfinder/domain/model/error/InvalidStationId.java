package com.comsystoreply.labs.stationfinder.domain.model.error;

import com.comsystoreply.labs.stationfinder.domain.model.*;

public class InvalidStationId extends RuntimeException {
    public InvalidStationId(StationId stationId) {
        super(String.format("StationId(value=%s) does not reference any station.", stationId));
    }
}
