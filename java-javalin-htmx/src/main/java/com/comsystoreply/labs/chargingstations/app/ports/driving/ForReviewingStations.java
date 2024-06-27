package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.Review;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;

import java.util.List;

public interface ForReviewingStations {
    List<Review> listStationReviews(User user, StationId id);

    void addStationReview(User user, StationId stationId, String reviewText);
}
