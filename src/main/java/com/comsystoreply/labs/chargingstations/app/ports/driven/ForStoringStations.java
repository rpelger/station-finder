package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.StationId;

import java.util.Collection;
import java.util.List;

public interface ForStoringStations {

    void saveAll(Collection<ChargingStation> stations);

    List<ChargingStation> findNear(Location location, Radius radius);

    void updateOperator(StationId id, String operator);

}
