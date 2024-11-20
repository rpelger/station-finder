package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import com.comsystoreply.labs.chargingstations.app.model.Radius;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.*;
import java.util.function.Function;

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
                .sorted(Comparator.comparing(s -> s.id().value()))
                .limit(10)
                .toList();
    }

    private static ChargingStation conflictError(ChargingStation cs1, ChargingStation cs2) {
        throw new RuntimeException("Conflict error");
    }
}
