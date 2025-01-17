package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;

import java.util.*;

public interface ForStoringStations {

    // Stations
    // --------
    void saveAll(Collection<Station> stations);

    List<Station> findInArea(Area area, PageRequest<Station> pageRequest);

    int countInArea(Area area);

    Station get(StationId stationId);

    List<Station> getStations(PageRequest<Station> pageRequest);

    boolean exists(StationId stationId);

    int count();

    void updateOperator(StationId id, String operator);


    // Station Reviews
    // ---------------
    Optional<Review> findReviewById(ReviewId reviewId);

    List<Review> findReviewsForStation(StationId stationId);

    void addReview(StationId id, Review review);

    void deleteReview(ReviewId reviewId);

}
