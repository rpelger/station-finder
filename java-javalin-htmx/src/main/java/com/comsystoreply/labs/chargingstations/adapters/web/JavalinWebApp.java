package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import io.javalin.Javalin;

public class JavalinWebApp {

    private final Javalin webapp;

    public JavalinWebApp(ChargingStationsApp app) {
        var stationRequestHandler = new StationRequestHandler(app);
        var userRequestHandler = new UserRequestHandler(app);

        // @formatter:off
        this.webapp = Javalin.create()
                .get ("/charging-stations/near/{lat}/{long}", stationRequestHandler::findNearestStations)
                .get ("/charging-stations/{id}", stationRequestHandler::stationDetails)
                .get ("/charging-stations/{id}/reviews", stationRequestHandler::stationReviews)
                .post("/charging-stations/{id}", stationRequestHandler::updateStationOperator)
                .post("/charging-stations/{id}/reviews", stationRequestHandler::addStationReview)

                .post("/auth/registrations", userRequestHandler::registerNewUser)
                .post("/auth/authentications", userRequestHandler::authenticateUser);
    }

    public void start(int port) {
        this.webapp.start(port);
    }
}
