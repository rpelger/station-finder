package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.*;

public class ViewStationAsADmin {
    private final ForStoringStations stationRepo;

    public ViewStationAsADmin(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Station execute(User user, StationId id) {
        Permissions.checkAllowed(user::isAdmin);
        return stationRepo.get(id);
    }
}
