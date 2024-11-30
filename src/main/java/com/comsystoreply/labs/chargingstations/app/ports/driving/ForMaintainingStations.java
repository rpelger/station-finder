package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.List;

public interface ForMaintainingStations {
    List<ChargingStation> listAll(User user);

    void importCurrentStations(User user);

    void updateStationOperator(User user, StationId stationId, String operator);
}
