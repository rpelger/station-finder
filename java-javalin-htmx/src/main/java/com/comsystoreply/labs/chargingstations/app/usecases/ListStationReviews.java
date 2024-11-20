package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.model.Review;
import com.comsystoreply.labs.chargingstations.app.model.StationId;
import com.comsystoreply.labs.chargingstations.app.model.User;

import java.util.List;

public class ListStationReviews implements UseCase {
    public List<Review> apply(User user, StationId stationId) {
        Permissions.checkAllowed(user, this);
        return null;
    }
}
