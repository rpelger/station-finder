package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.UseCase;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.ForStoringStations;

import java.util.List;

public class ListStationReviews implements UseCase {
    private final ForStoringStations stationRepo;

    public ListStationReviews(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public List<Review> apply(User user, StationId stationId) {
        Permissions.checkAllowed(user, this);
        return stationRepo.findAllStationReviews(stationId);
    }
}
