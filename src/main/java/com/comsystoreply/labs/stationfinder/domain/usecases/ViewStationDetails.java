package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class ViewStationDetails implements UseCase {
    private final ForStoringStations stationRepo;

    public ViewStationDetails(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Station apply(User.RegularUser consumer, StationId stationId) {
        return stationRepo.get(stationId);
    }
}
