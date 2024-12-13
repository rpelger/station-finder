package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.*;

public interface ForStoringStations {

    void saveAll(Collection<Station> stations);

    List<Station> findNear(Geo geo, Radius radius);

    Station get(StationId stationId);

    List<Station> getAll();

    boolean exists(StationId stationId);

    void updateOperator(StationId id, String operator);

    List<Review> findAllStationReviews(StationId stationId);

    void addReview(StationId id, Review review);

    void deleteReview(ReviewId reviewId);

    Optional<Review> find(ReviewId reviewId);
}
