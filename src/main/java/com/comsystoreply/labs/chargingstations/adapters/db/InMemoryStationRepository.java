package com.comsystoreply.labs.chargingstations.adapters.db;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.error.InvalidStationId;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class InMemoryStationRepository implements ForStoringStations {

    private final Map<StationId, Station> stationsMap = new HashMap<>();
    private final Map<StationId, List<Review>> reviewsMap = new HashMap<>();

    @Override
    public void saveAll(Collection<Station> stations) {
        stationsMap.clear();
        stationsMap.putAll(stations.stream().collect(toMap(
                Station::id,
                Function.identity())));
    }

    @Override
    public List<Station> findNear(Geo geo, Radius radius) {
        return stationsMap.values().stream()
                .sorted(Comparator.comparing(s -> s.id().value()))
                .limit(10)
                .toList();
    }

    @Override
    public Station get(StationId id) {
        return Optional.ofNullable(stationsMap.get(id))
                .orElseThrow(() -> new InvalidStationId(id));
    }

    @Override
    public List<Station> getAll() {
        return stationsMap.values().stream()
                .sorted(Comparator.comparing(s -> s.location().address().zipCode()))
                .toList();
    }

    @Override
    public boolean exists(StationId stationId) {
        return Optional.ofNullable(stationsMap.get(stationId)).isPresent();
    }

    @Override
    public void updateOperator(StationId id, String operator) {
        var updatedStation = Optional.ofNullable(stationsMap.get(id))
                .map(station -> station.withOperator(operator))
                .orElseThrow(() -> new InvalidStationId(id));
        stationsMap.put(id, updatedStation);
    }

    @Override
    public void addReview(StationId id, Review review) {
        var reviews = reviewsMap.getOrDefault(id, new ArrayList<>());
        reviews.add(review);
        reviewsMap.put(id, reviews);
    }

    @Override
    public List<Review> findAllStationReviews(StationId stationId) {
        return reviewsMap.getOrDefault(stationId, List.of());
    }
}
