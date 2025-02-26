package com.comsystoreply.labs.stationfinder.domain;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driving.*;
import com.comsystoreply.labs.stationfinder.domain.usecases.*;

import java.util.*;

public class StationFinderApp implements
        ForAccessingTheApplication,
        ForAdministeringTheApplication,
        ForUsingTheApplication,
        ForAutomatedProcessesOfApplication {

    private final RegisterUser registerUser;
    private final AuthenticateUser authenticateUser;
    private final ListAllStationsPaged listAllStationsPaged;
    private final ViewStationAsADmin viewStationAsAdmin;
    private final FindNearestStations findNearestStations;
    private final ViewStationDetails viewStationDetails;
    private final ImportStations importChargingStations;
    private final UpdateStationOperator updateStationOperator;
    private final AddStationReview addStationReview;
    private final ListStationReviews listStationReviews;
    private final DeleteStationReview deleteStationReview;

    public StationFinderApp(
            ForObtainingStations stationLoader,
            ForStoringStations stationRepo,
            ForStoringUsers userRepo) {

        registerUser = new RegisterUser(userRepo);
        authenticateUser = new AuthenticateUser(userRepo);
        findNearestStations = new FindNearestStations(stationRepo);
        listAllStationsPaged = new ListAllStationsPaged(stationRepo);
        viewStationDetails = new ViewStationDetails(stationRepo);
        viewStationAsAdmin = new ViewStationAsADmin(stationRepo);
        importChargingStations = new ImportStations(stationLoader, stationRepo);
        updateStationOperator = new UpdateStationOperator(stationRepo);
        listStationReviews = new ListStationReviews(stationRepo);
        addStationReview = new AddStationReview(stationRepo);
        deleteStationReview = new DeleteStationReview(stationRepo);
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
    public Paged<Station> listAllStationsPaged(User.Admin admin, PageRequest<Station> pageRequest) {
        return listAllStationsPaged.apply(admin, pageRequest);
    }

    @Override
    public Station viewStationAsAdmin(User.Admin admin, StationId stationId) {
        return viewStationAsAdmin.apply(admin, stationId);
    }

    @Override
    public void importCurrentStations(User.Admin admin) {
        importChargingStations.apply(admin);
    }

    @Override
    public void updateStationOperator(User.Admin admin, StationId stationId, String operator) {
        updateStationOperator.apply(admin, stationId, operator);
    }

    @Override
    public Paged<Station> findStationsInAreaPaged(Area area, PageRequest<Station> pageRequest) {
        return findNearestStations.apply(area, pageRequest);
    }

    @Override
    public Station viewStationDetails(User.RegularUser consumer, StationId stationId) {
        return viewStationDetails.apply(consumer, stationId);
    }

    @Override
    public List<Review> listStationReviews(User.RegularUser consumer, StationId stationId) {
        return listStationReviews.apply(consumer, stationId);
    }

    @Override
    public Review addStationReview(User.RegularUser consumer, StationId stationId, String reviewText) {
        return addStationReview.apply(consumer, stationId, reviewText);
    }

    @Override
    public void deleteStationReview(User.RegularUser consumer, ReviewId reviewId) {
        deleteStationReview.apply(consumer, reviewId);
    }

    @Override
    public void importCurrentStations(User.SystemUser systemUser) {
        importChargingStations.apply(systemUser);
    }
}
