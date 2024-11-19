package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.Review;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForReviewingStations;

import java.util.List;

public class StationReviewer implements ForReviewingStations {

    @Override
    public List<Review> listStationReviews(User user, StationId id) {
        return List.of();
    }

    @Override
    public void addStationReview(User user, StationId stationId, String reviewText) {

    }
}
