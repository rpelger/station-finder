package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.Station;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.List;

public class ListAllStations implements UseCase {
    private final ForStoringStations stationsRepo;

    public ListAllStations(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public List<Station> apply(User user) {
        Permissions.checkAllowed(user::isAdmin);
        return stationsRepo.getAll();
    }
}
