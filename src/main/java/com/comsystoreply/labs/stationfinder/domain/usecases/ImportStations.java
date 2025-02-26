package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class ImportStations implements UseCase {

    private final ForObtainingStations stationLoader;
    private final ForStoringStations stationRepo;

    public ImportStations(ForObtainingStations stationLoader, ForStoringStations stationRepo) {
        this.stationLoader = stationLoader;
        this.stationRepo = stationRepo;
    }

    public void apply(User.SystemUser systemUser) {
        var stations = stationLoader.fetchStations();
        stationRepo.saveAll(stations);
    }

    public void apply(User.Admin admin) {
        var stations = stationLoader.fetchStations();
        stationRepo.saveAll(stations);
    }
}
