package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class UpdateStationOperator implements UseCase{
    private final ForStoringStations stationRepo;

    public UpdateStationOperator(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public void apply(User user, StationId stationId, String operator) {
        try {
            Permissions.checkAllowed(user, this);
            stationRepo.updateOperator(stationId, operator);
        } catch (IllegalArgumentException e) {
            throw new UpdateStationOperator.InvalidStationId(stationId) ;
        }
    }

    public static class InvalidStationId extends RuntimeException {
        public InvalidStationId(StationId stationId) {
            super("Error updating station operator. Station with id=" + stationId + " does not exist");
        }
    }
}
