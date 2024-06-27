package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.StationId;
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
    public void refreshChargingStations() {
        var stations = stationLoader.loadAllChargingStations();
        stationRepository.saveAll(stations);
    }

    @Override
    public void updateStationOperator(StationId stationId, String operator) {

    }
}
