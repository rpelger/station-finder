package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class UpdateStationOperator implements UseCase {
    private final ForStoringStations stationRepo;

    public UpdateStationOperator(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public void apply(User user, StationId stationId, String operator) {
        Permissions.checkAllowed(user, this);
        stationRepo.updateOperator(stationId, operator);
    }
}
