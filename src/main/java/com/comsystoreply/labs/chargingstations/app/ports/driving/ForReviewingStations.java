package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.List;

public interface ForReviewingStations {
    List<Review> listStationReviews(User user, StationId id);

    Review addStationReview(User user, StationId stationId, String reviewText);

    void deleteStationReview(User user, ReviewId reviewId);
}
