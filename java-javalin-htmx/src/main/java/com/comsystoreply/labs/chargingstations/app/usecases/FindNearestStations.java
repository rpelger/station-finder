package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.List;

public class FindNearestStations implements UseCase{
    private final ForStoringStations stationRepo;

    public FindNearestStations(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public List<ChargingStation> apply(User user, Location location, Radius radius) {
        Permissions.checkAllowed(user, this);
        return stationRepo.findNear(location, radius);
    }
}
