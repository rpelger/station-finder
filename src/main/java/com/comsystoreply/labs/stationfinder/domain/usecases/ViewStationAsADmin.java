package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class ViewStationAsADmin {
    private final ForStoringStations stationRepo;

    public ViewStationAsADmin(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Station apply(User.Admin admin, StationId id) {
        return stationRepo.get(id);
    }
}
