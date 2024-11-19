package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.model.*;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class StationRequestHandler {

    private final ChargingStationsApp app;

    public StationRequestHandler(ChargingStationsApp app) {
        this.app = app;
    }

    public void findNearestStations(Context context) {
        Double lat = context.pathParamAsClass("lat", Double.class).getOrThrow(err -> new RuntimeException());
        Double lon = context.pathParamAsClass("long", Double.class).getOrThrow(err -> new RuntimeException());
        Double rad = context.queryParamAsClass("radius", Double.class).getOrDefault(10.0d);

        var user = new User();
        var location = new Location(new Geo(lat, lon), null);
        var radius = new Radius(rad);
        var stations = app.findNearestStations(user, location, radius);

        context.json(stations);
    }

    public void stationDetails(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());

        var chargingStation = app.viewStationDetails(new User(), new StationId(id));

        context.json(chargingStation);
    }

    public void updateStationOperator(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var updateStationRequest = context.bodyValidator(UpdateStationRequest.class).getOrThrow(err -> new RuntimeException());

        app.updateStationOperator(new User(), new StationId(id), updateStationRequest.operator);

        context.status(HttpStatus.OK);
    }

    public void stationReviews(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());

        var reviews = app.listStationReviews(new User(), new StationId(id));

        context.json(reviews);
    }

    public void addStationReview(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var addStationReviewRequest = context.bodyValidator(AddStationReviewRequest.class).getOrThrow(err -> new RuntimeException());

        app.addStationReview(
                new User(),
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
