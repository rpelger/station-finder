package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.List;

public class ListStationReviews implements UseCase {
    public List<Review> apply(User user, StationId stationId) {
        Permissions.checkAllowed(user, this);
        return null;
    }
}
