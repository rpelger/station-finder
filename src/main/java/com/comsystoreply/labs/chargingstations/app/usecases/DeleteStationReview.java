package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.ReviewId;
import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class DeleteStationReview implements UseCase {

    private final ForStoringStations stationRepo;

    public DeleteStationReview(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public void apply(User user, ReviewId reviewId) {
        var maybeReview = stationRepo.find(reviewId);
        if (maybeReview.isPresent()) {
            Permissions.checkAllowed(() -> user.isAdmin() || user.isConsumer() && maybeReview.get().createdBy().equals(user.id()));
            stationRepo.deleteReview(reviewId);
        }
    }
}
