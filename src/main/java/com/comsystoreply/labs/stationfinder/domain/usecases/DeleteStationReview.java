package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class DeleteStationReview implements UseCase {

    private final ForStoringStations stationRepo;

    public DeleteStationReview(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public void apply(User.RegularUser consumer, ReviewId reviewId) {
        var review = stationRepo.findReviewById(reviewId);
        if (review.isPresent()) {
            Permissions.checkAllowed(() -> review.get().isCreatedBy(consumer.user));
            stationRepo.deleteReview(reviewId);
        }
    }
}
