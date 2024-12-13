package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.adapters.web.error.InvalidRequestParam;
import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class StationRequestHandler {

    private final StationFinderApp app;

    public StationRequestHandler(StationFinderApp app) {
        this.app = app;
    }

    private static User getUser(Context context) {
        return Optional
                .ofNullable(context.<User>sessionAttribute("current_user"))
                .orElse(User.CONSUMER_USER);
    }

    public void findNearestStations(Context context) {
        Double lat = context.pathParamAsClass("lat", Double.class).getOrThrow(InvalidRequestParam::new);
        Double lon = context.pathParamAsClass("long", Double.class).getOrThrow(InvalidRequestParam::new);
        Double rad = context.queryParamAsClass("radius", Double.class).getOrDefault(10.0d);

        var user = getUser(context);
        var geo = new Geo(lat, lon);
        var radius = new Radius(rad);

        var stationsResponse = app
                .findNearestStations(user, geo, radius)
                .stream()
                .map(StationResponse::new)
                .toList();

        context.json(stationsResponse);
    }

    public void stationDetails(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var user = getUser(context);

        var chargingStation = app.viewStationDetails(user, new StationId(id));

        context.json(chargingStation);
    }

    public void updateStationOperator(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var updateStationRequest = context.bodyValidator(UpdateStationRequest.class).getOrThrow(err -> new RuntimeException());
        var user = getUser(context);

        app.updateStationOperator(user, new StationId(id), updateStationRequest.operator);

        context.status(HttpStatus.OK);
    }

    public void stationReviews(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var user = getUser(context);

        var reviews = app.listStationReviews(user, new StationId(id));

        context.json(reviews);
    }

    public void addStationReview(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var addStationReviewRequest = context.bodyValidator(AddStationReviewRequest.class).getOrThrow(err -> new RuntimeException());
        var user = getUser(context);

        var review = app.addStationReview(
                user,
                new StationId(id),
                addStationReviewRequest.reviewText
        );

        context.json(review).status(HttpStatus.CREATED);

    }

    public void listStations(Context context) {
        var user = getUser(context);
        var stations = app.listAll(user);

        context.json(stations);
    }

    public void deleteStationReview(Context context) {
        var user = getUser(context);
        // var stationId = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var reviewId = context.pathParamAsClass("reviewId", String.class).getOrThrow(err -> new RuntimeException());
        app.deleteStationReview(user, new ReviewId(reviewId));
    }

    record StationResponse(
            String id,
            String operator,
            String startOfServiceDate,
            Location location,
            List<Charger> chargers) {
        StationResponse(Station station) {
            this(station.id().value(), station.operator(), station.startOfService(), station.location(), station.chargers());
        }
    }

    private record UpdateStationRequest(String operator) {
    }

    private record AddStationReviewRequest(String reviewText) {
    }
}
