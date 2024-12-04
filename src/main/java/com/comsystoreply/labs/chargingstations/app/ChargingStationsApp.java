package com.comsystoreply.labs.chargingstations.app;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.*;
import com.comsystoreply.labs.chargingstations.app.ports.driving.*;
import com.comsystoreply.labs.chargingstations.app.usecases.*;

import java.util.List;

public class ChargingStationsApp implements ForAccessingPlatform, ForMaintainingStations, ForFindingStations, ForReviewingStations {

    private final RegisterUser registerUser;
    private final AuthenticateUser authenticateUser;
    private final ListAllStations listAllStations;
    private final FindNearestStations findNearestStations;
    private final ViewStationDetails viewStationDetails;
    private final ImportChargingStations importChargingStations;
    private final UpdateStationOperator updateStationOperator;
    private final AddStationReview addStationReview;
    private final ListStationReviews listStationReviews;

    public ChargingStationsApp(
            ForObtainingStations stationLoader,
            ForStoringStations stationRepo,
            ForStoringUsers userRepo) {

        registerUser = new RegisterUser(userRepo);
        authenticateUser = new AuthenticateUser(userRepo);
        findNearestStations = new FindNearestStations(stationRepo);
        listAllStations = new ListAllStations(stationRepo);
        viewStationDetails = new ViewStationDetails(stationRepo);
        importChargingStations = new ImportChargingStations(stationLoader, stationRepo);
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
    public List<ChargingStation> findNearestStations(User user, Location location, Radius radius) {
        return findNearestStations.apply(user, location, radius);
    }

    @Override
    public ChargingStation viewStationDetails(User user, StationId stationId) {
        return viewStationDetails.apply(user, stationId);
    }

    @Override
    public List<ChargingStation> listAll(User user) {
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
    public void addStationReview(User user, StationId stationId, String reviewText) {
        addStationReview.apply(user, stationId, reviewText);
    }
}
