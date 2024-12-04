package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;
import com.comsystoreply.labs.chargingstations.app.ports.driven.error.StationNotFound;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class InMemoryStationRepository implements ForStoringStations {

    private final Map<StationId, ChargingStation> stationsMap = new HashMap<>();
    private final Map<StationId, List<Review>> reviewsMap = new HashMap<>();

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

    @Override
    public ChargingStation get(StationId id) throws StationNotFound {
        return Optional.ofNullable(stationsMap.get(id))
                .orElseThrow(() -> new StationNotFound(id));
    }

    @Override
    public List<ChargingStation> getAll() {
        return stationsMap.values().stream()
                .sorted(Comparator.comparing(s -> s.location().address().zipCode()))
                .toList();
    }

    @Override
    public boolean exists(StationId stationId) {
        return Optional.ofNullable(stationsMap.get(stationId)).isPresent();
    }

    @Override
    public void updateOperator(StationId id, String operator) throws StationNotFound {
        var updatedStation = Optional.ofNullable(stationsMap.get(id))
                .map(station -> station.withOperator(operator))
                .orElseThrow(() -> new StationNotFound(id));
        stationsMap.put(id, updatedStation);
    }

    @Override
    public void addReview(StationId id, Review review) {
        if(reviewsMap.containsKey(id)) {
            var reviews = reviewsMap.get(id);
            reviews.add(review);
            reviewsMap.put(id, reviews);
        } else {
            reviewsMap.put(id, List.of(review));
        }
    }

    @Override
    public List<Review> findAllStationReviews(StationId stationId) {
        return reviewsMap.getOrDefault(stationId, List.of());
    }

    private static ChargingStation conflictError(ChargingStation cs1, ChargingStation cs2) {
        throw new RuntimeException("Conflict error");
    }
}
