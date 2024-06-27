package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;

import java.util.Collection;

public interface ForStoringStations {

    void saveAll(Collection<ChargingStation> stations);

}
