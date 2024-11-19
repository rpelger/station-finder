package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForFindingStations;

import java.util.List;

public class StationFinder implements ForFindingStations {

    @Override
    public List<ChargingStation> findNearestStations(User user, Location location, Radius radius) {
        return List.of();
    }

    @Override
    public ChargingStation viewStationDetails(User user, StationId stationId) {
        return null;
    }
}
