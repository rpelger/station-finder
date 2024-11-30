package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.usecases.error.ImportStationsError;

public class ImportChargingStations implements UseCase {
    private final ForObtainingStations stationLoader;
    private final ForStoringStations stationRepo;

    public ImportChargingStations(ForObtainingStations stationLoader, ForStoringStations stationRepo) {
        this.stationLoader = stationLoader;
        this.stationRepo = stationRepo;
    }

    public void apply(User user) {
        Permissions.checkAllowed(user, this);
        try {
            var stations = stationLoader.fetchCurrentStations();
            stationRepo.saveAll(stations);
        } catch (Exception e) {
            throw new ImportStationsError(e);
        }
    }
}
