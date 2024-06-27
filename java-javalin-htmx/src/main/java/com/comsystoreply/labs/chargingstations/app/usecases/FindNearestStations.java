package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.List;

public class FindNearestStations {
    public FindNearestStations(ForStoringStations stationRepo) {

    }

    public List<ChargingStation> apply(User user, Location location, Radius radius) {
        return null;
    }
}
