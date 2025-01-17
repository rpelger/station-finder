package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.adapters.web.error.*;
import com.comsystoreply.labs.chargingstations.adapters.web.utils.*;
import com.comsystoreply.labs.chargingstations.app.*;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import io.javalin.http.*;

public class StationJsonApiHandler {

    private final StationFinderApp app;

    public StationJsonApiHandler(StationFinderApp app) {
        this.app = app;
    }

    public void findNearestStations(Context context) {
        Double lat = context.pathParamAsClass("lat", Double.class).getOrThrow(InvalidRequestParam::new);
        Double lon = context.pathParamAsClass("long", Double.class).getOrThrow(InvalidRequestParam::new);
        Double rad = context.queryParamAsClass("radius", Double.class).getOrDefault(10.0d);

        var user = Auth.getUser(context);
        var geo = new Geo(lat, lon);
        var radius = new Radius(rad);
        var area = new Area(geo, radius);

        var stationsResponse = app
                .findStationsInAreaPaged(user, area, new StationPageRequest(1, Integer.MAX_VALUE))
                .mapItems(StationResponse::new);

        context.json(stationsResponse).contentType("application/json; charset=utf-8");
    }

    public void stationDetails(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var user = Auth.getUser(context);

        var chargingStation = app.viewStationDetails(user, new StationId(id));

        context.json(chargingStation);
    }

    public void updateStationOperator(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var updateStationRequest = context.bodyValidator(UpdateStationRequest.class).getOrThrow(err -> new RuntimeException());
        var user = Auth.getUser(context);

        app.updateStationOperator(user, new StationId(id), updateStationRequest.operator);

        context.status(HttpStatus.OK);
    }

    public void stationReviews(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var user = Auth.getUser(context);

        var reviews = app.listStationReviews(user, new StationId(id));

        context.json(reviews);
    }

    public void addStationReview(Context context) {
        var id = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var addStationReviewRequest = context.bodyValidator(AddStationReviewRequest.class).getOrThrow(err -> new RuntimeException());
        var user = Auth.getUser(context);

        var review = app.addStationReview(
                user,
                new StationId(id),
                addStationReviewRequest.reviewText
        );

        context.json(review).status(HttpStatus.CREATED);

    }

    public void listStationsPaged(Context context) {
        var user = Auth.getUser(context);
        var stations = app.getStationsPaged(user, new StationPageRequest());

        context.json(stations);
    }

    public void deleteStationReview(Context context) {
        var user = Auth.getUser(context);
        // var stationId = context.pathParamAsClass("id", String.class).getOrThrow(err -> new RuntimeException());
        var reviewId = context.pathParamAsClass("reviewId", String.class).getOrThrow(err -> new RuntimeException());
        app.deleteStationReview(user, new ReviewId(reviewId));
    }

    private record UpdateStationRequest(String operator) {
    }

    private record AddStationReviewRequest(String reviewText) {
    }
}
