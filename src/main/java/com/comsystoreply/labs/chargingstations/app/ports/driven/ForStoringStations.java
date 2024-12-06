package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.Collection;
import java.util.List;

public interface ForStoringStations {

    void saveAll(Collection<Station> stations);

    List<Station> findNear(Geo geo, Radius radius);

    Station get(StationId stationId);

    List<Station> getAll();

    boolean exists(StationId stationId);

    void updateOperator(StationId id, String operator);

    void addReview(StationId id, Review review);

    List<Review> findAllStationReviews(StationId stationId);
}
