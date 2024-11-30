package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.usecases.error.InvalidStationId;

public class ViewStationDetails implements UseCase {
    private final ForStoringStations stationRepo;

    public ViewStationDetails(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public ChargingStation apply(User user, StationId stationId) {
        Permissions.checkAllowed(user, this);
        try {
            return stationRepo.get(stationId);
        } catch (IllegalArgumentException e) {
            throw new InvalidStationId(this, stationId);
        }
    }
}
