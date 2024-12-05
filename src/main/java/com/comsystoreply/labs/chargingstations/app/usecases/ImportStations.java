package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class ImportStations implements UseCase {

    private final ForObtainingStations stationLoader;
    private final ForStoringStations stationRepo;

    public ImportStations(ForObtainingStations stationLoader, ForStoringStations stationRepo) {
        this.stationLoader = stationLoader;
        this.stationRepo = stationRepo;
    }

    public void apply(User user) {
        Permissions.checkAllowed(user, this);
        var stations = stationLoader.fetchCurrentStations();
        stationRepo.saveAll(stations);
    }
}
