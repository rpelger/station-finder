package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

import java.util.*;

public class ListStationReviews implements UseCase {
    private final ForStoringStations stationRepo;

    public ListStationReviews(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public List<Review> apply(User.RegularUser consumer, StationId stationId) {
        return stationRepo.findReviewsForStation(stationId);
    }
}
