package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForFindingStations;

import java.util.List;

public class StationFinder implements ForFindingStations {

    @Override
    public List<ChargingStation> findNearestStations(Location location, Radius radius) {
        return List.of();
    }

    @Override
    public ChargingStation viewStationDetails(StationId id) {
        return null;
    }
}
