package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class AddStationReview implements UseCase {

    private final ForStoringStations stationsRepo;

    public AddStationReview(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public Review apply(User.RegularUser consumer, StationId stationId, String comment) {
        if (!stationsRepo.exists(stationId)) {
            throw new InvalidStationId(stationId);
        }

        var review = new Review(consumer.user, comment);
        stationsRepo.addReview(stationId, review);
        return review;
    }
}
