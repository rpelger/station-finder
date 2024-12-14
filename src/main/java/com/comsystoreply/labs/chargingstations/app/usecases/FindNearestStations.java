package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.List;

public class FindNearestStations implements UseCase {
    private final ForStoringStations stationRepo;

    public FindNearestStations(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public List<Station> apply(User user, Geo geo, Radius radius) {
        Permissions.checkAllowed(() -> user.isConsumer() || user.isGuest());
        return stationRepo.findNear(geo, radius);
    }
}
