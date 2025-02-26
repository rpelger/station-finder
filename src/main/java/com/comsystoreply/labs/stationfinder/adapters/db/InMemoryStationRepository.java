package com.comsystoreply.labs.stationfinder.adapters.db;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;
import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.function.*;

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
    public List<Station> findInArea(Area area, PageRequest<Station> pageRequest) {
        return stationsMap.values().stream()
                .filter(station -> area.contains(station.location().geo()))
                .sorted(pageRequest.comparator())
                .skip(pageRequest.offset())
                .limit(pageRequest.limit())
                .toList();
    }

    @Override
    public int countInArea(Area area) {
        return Long.valueOf(stationsMap.values().stream()
                .filter(station -> area.contains(station.location().geo()))
                .count()).intValue();
    }

    @Override
    public Station get(StationId id) {
        return Optional.ofNullable(stationsMap.get(id))
                .orElseThrow(() -> new InvalidStationId(id));
    }

    @Override
    public List<Station> getStations(PageRequest<Station> pageRequest) {
        List<Station> list = stationsMap.values().stream()
                .sorted(pageRequest.comparator())
                .skip(pageRequest.offset())
                .limit(pageRequest.limit())
                .toList();
        return list;
    }

    @Override
    public boolean exists(StationId stationId) {
        return Optional.ofNullable(stationsMap.get(stationId)).isPresent();
    }

    @Override
    public int count() {
        return stationsMap.size();
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
    public List<Review> findReviewsForStation(StationId stationId) {
        return reviewsMap.getOrDefault(stationId, List.of());
    }

    @Override
    public void deleteReview(ReviewId reviewId) {
        reviewsMap.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(review -> review.id().equals(reviewId)))
                .forEach(entry -> entry.setValue(withRemoved(reviewId, entry.getValue())));
    }

    @Override
    public Optional<Review> findReviewById(ReviewId reviewId) {
        return reviewsMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().filter(review -> review.id().equals(reviewId)))
                .findFirst();
    }

    private List<Review> withRemoved(ReviewId reviewId, List<Review> value) {
        var newList = new ArrayList<>(value);
        newList.removeIf(review -> review.id().equals(reviewId));
        return newList;
    }
}
