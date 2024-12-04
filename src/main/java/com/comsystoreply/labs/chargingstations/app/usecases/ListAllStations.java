package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.List;

public class ListAllStations implements UseCase {
    private final ForStoringStations stationsRepo;

    public ListAllStations(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public List<ChargingStation> apply(User user) {
        Permissions.checkAllowed(user, this);
        return stationsRepo.getAll();
    }
}
