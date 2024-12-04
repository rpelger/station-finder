package com.comsystoreply.labs.chargingstations.app.ports.driven.error;

import com.comsystoreply.labs.chargingstations.app.model.StationId;

public class StationNotFound extends Exception {

    public StationNotFound(StationId stationId) {
        super(String.format("Station(id=%s) does not exist.", stationId.value()));
    }
}
