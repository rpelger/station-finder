package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.ports.driven.error.StationNotFound;
import com.comsystoreply.labs.chargingstations.app.usecases.error.InvalidStationId;

public class UpdateStationOperator implements UseCase {
    private final ForStoringStations stationRepo;

    public UpdateStationOperator(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public void apply(User user, StationId stationId, String operator) {
        Permissions.checkAllowed(user, this);
        try {
            stationRepo.updateOperator(stationId, operator);
        } catch (StationNotFound e) {
            throw new InvalidStationId(this, stationId);
        }
    }
}
