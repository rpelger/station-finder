package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class ViewStationDetails implements UseCase {
    private final ForStoringStations stationRepo;

    public ViewStationDetails(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Station apply(User user, StationId stationId) {
        Permissions.checkAllowed(user, this);
        return stationRepo.get(stationId);
    }
}
