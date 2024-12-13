package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.error.InvalidStationId;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

public class AddStationReview implements UseCase {

    private final ForStoringStations stationsRepo;

    public AddStationReview(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public Review apply(User user, StationId stationId, String comment) {
        Permissions.checkAllowed(user::isConsumer);
        if (!stationsRepo.exists(stationId)) {
            throw new InvalidStationId(stationId);
        }

        var review = new Review(user, comment);
        stationsRepo.addReview(stationId, review);
        return review;
    }
}
