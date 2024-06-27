package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class ViewStationDetails {
    public ViewStationDetails(ForStoringStations stationRepo) {

    }

    public ChargingStation apply(User user, StationId stationId) {
        return null;
    }
}
