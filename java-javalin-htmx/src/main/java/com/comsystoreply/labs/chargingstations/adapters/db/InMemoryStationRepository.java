package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class InMemoryStationRepository implements ForStoringStations {

    private final Map<StationId, ChargingStation> stationsMap = new HashMap<>();

    @Override
    public void saveAll(Collection<ChargingStation> stations) {
        stationsMap.clear();
        stationsMap.putAll(stations.stream().collect(toMap(
                ChargingStation::id,
                Function.identity(),
                InMemoryStationRepository::conflictError)));
    }

    @Override
    public List<ChargingStation> findNear(Location location, Radius radius) {
        return stationsMap.values().stream()
                .filter(station -> station.location().address().zipCode().equals(location.address().zipCode()))
                .limit(50)
                .toList();
    }

    private static ChargingStation conflictError(ChargingStation cs1, ChargingStation cs2) {
        throw new RuntimeException("Conflict error");
    }
}
