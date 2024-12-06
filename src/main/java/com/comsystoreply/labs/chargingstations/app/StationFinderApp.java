package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.*;
import com.comsystoreply.labs.chargingstations.app.usecases.*;

import java.util.List;

public class StationFinderApp implements ForAccessingPlatform, ForMaintainingStations, ForFindingStations, ForReviewingStations {

    private final RegisterUser registerUser;
    private final AuthenticateUser authenticateUser;
    private final ListAllStations listAllStations;
    private final FindNearestStations findNearestStations;
    private final ViewStationDetails viewStationDetails;
    private final ImportStations importChargingStations;
    private final UpdateStationOperator updateStationOperator;
    private final AddStationReview addStationReview;
    private final ListStationReviews listStationReviews;

    public StationFinderApp(
            ForObtainingStations stationLoader,
            ForStoringStations stationRepo,
            ForStoringUsers userRepo) {

        registerUser = new RegisterUser(userRepo);
        authenticateUser = new AuthenticateUser(userRepo);
        findNearestStations = new FindNearestStations(stationRepo);
        listAllStations = new ListAllStations(stationRepo);
        viewStationDetails = new ViewStationDetails(stationRepo);
        importChargingStations = new ImportStations(stationLoader, stationRepo);
        updateStationOperator = new UpdateStationOperator(stationRepo);
        listStationReviews = new ListStationReviews(stationRepo);
        addStationReview = new AddStationReview(stationRepo);
    }

    @Override
    public User regigsterNewUser(UserRegistration registration) {
        return registerUser.apply(registration);
    }

    @Override
    public User authenticateUser(UserCredentials credentials) {
        return authenticateUser.apply(credentials);
    }

    @Override
    public List<Station> findNearestStations(User user, Geo geo, Radius radius) {
        return findNearestStations.apply(user, geo, radius);
    }

    @Override
    public Station viewStationDetails(User user, StationId stationId) {
        return viewStationDetails.apply(user, stationId);
    }

    @Override
    public List<Station> listAll(User user) {
        return listAllStations.apply(user);
    }

    @Override
    public void importCurrentStations(User user) {
        importChargingStations.apply(user);
    }

    @Override
    public void updateStationOperator(User user, StationId stationId, String operator) {
        updateStationOperator.apply(user, stationId, operator);
    }

    @Override
    public List<Review> listStationReviews(User user, StationId stationId) {
        return listStationReviews.apply(user, stationId);
    }

    @Override
    public Review addStationReview(User user, StationId stationId, String reviewText) {
        return addStationReview.apply(user, stationId, reviewText);
    }
}
