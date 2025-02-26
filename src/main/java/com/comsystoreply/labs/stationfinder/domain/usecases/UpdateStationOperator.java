package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class UpdateStationOperator implements UseCase {
    private final ForStoringStations stationRepo;

    public UpdateStationOperator(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public void apply(User.Admin admin, StationId stationId, String operator) {
        stationRepo.updateOperator(stationId, operator);
    }
}
