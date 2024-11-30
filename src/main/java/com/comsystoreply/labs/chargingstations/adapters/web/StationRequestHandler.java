package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.model.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class StationRequestHandler {

    private final ChargingStationsApp app;

    public StationRequestHandler(ChargingStationsApp app) {
        this.app = app;
    }

    public void findNearestStations(Context context) {
        Double lat = context.pathParamAsClass("lat", Double.class).getOrThrow(err -> new RuntimeException());
        Double lon = context.pathParamAsClass("long", Double.class).getOrThrow(err -> new RuntimeException());
        Double rad = context.queryParamAsClass("radius", Double.class).getOrDefault(10.0d);

        var user = getUser(context);
        var location = new Location(new Geo(lat, lon), new Address("X-Street", "47", "80992", "Bonn", "NRW", "Germany"));
        var radius = new Radius(rad);
        var stationsResponse = app
                .findNearestStations(user, location, radius)
                .stream()
                .map(s -> new StationResponse(
                        s.id().value(),
                        s.operator(),
                        s.startOfService(),
                        s.location(),
                        s.chargers()))
                .toList();

        context.json(stationsResponse);
    }

    record StationResponse(
            String id,
            String operator,
            String startOfServiceDate,
            Location location,
            List<Charger> chargers) {
    }

    private static User getUser(Context context) {
        return Optional
                .ofNullable(context.<User>sessionAttribute("current_user"))
                .orElse(User.DUMMY_USER);
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

        app.addStationReview(
                user,
                new StationId(id),
                addStationReviewRequest.reviewText
        );

        context.status(HttpStatus.OK);

    }

    private record UpdateStationRequest(String operator) {
    }

    private record AddStationReviewRequest(String reviewText) {
    }
}
