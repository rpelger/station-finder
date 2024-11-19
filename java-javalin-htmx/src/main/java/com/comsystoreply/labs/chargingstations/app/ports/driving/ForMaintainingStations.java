package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;

public interface ForMaintainingStations {
    void importCurrentStations();
    void updateStationOperator(User user, StationId stationId, String operator);
}
