package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.usecases.error.InvalidStationId;

public class AddStationReview implements UseCase{

    private final ForStoringStations stationsRepo;

    public AddStationReview(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public void apply(User user, StationId stationId, String comment) {
        Permissions.checkAllowed(user, this);
        if(!stationsRepo.exists(stationId)) {
            throw new InvalidStationId(this, stationId);
        }

        stationsRepo.addReview(stationId, new Review(user, comment));
    }
}
