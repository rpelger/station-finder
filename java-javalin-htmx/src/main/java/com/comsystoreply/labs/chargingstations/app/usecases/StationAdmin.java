package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForMaintainingStations;

public class StationAdmin implements ForMaintainingStations {

    private final ForObtainingStations stationLoader;
    private final ForStoringStations stationRepository;

    public StationAdmin(ForObtainingStations stationLoader, ForStoringStations stationRepository) {
        this.stationLoader = stationLoader;
        this.stationRepository = stationRepository;
    }

    @Override
    public void importCurrentStations() {
        var stations = stationLoader.fetchCurrentStations();
        stationRepository.saveAll(stations);
    }

    @Override
    public void updateStationOperator(User user, StationId stationId, String operator) {

    }
}
