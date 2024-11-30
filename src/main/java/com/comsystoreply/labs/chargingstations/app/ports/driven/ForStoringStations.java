package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.Collection;
import java.util.List;

public interface ForStoringStations {

    void saveAll(Collection<ChargingStation> stations);

    List<ChargingStation> findNear(Location location, Radius radius);

    void updateOperator(StationId id, String operator);

}
