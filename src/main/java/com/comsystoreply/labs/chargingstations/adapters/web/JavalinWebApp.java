package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.usecases.error.*;
import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                .put ("/charging-stations/{id}", stationRequestHandler::updateStationOperator)
                .post("/charging-stations/{id}/reviews", stationRequestHandler::addStationReview)

                .post("/auth/registrations", userRequestHandler::registerNewUser)
                .post("/auth/authentications", userRequestHandler::authenticateUser)

                .exception(Unauthorized.class,      (ex, ctx) -> ctx.status(403).json(error(403, ex)))
                .exception(UserAlreadyExists.class, (ex, ctx) -> ctx.status(409).json(error(409, ex)))
                .exception(BadCredentials.class,    (ex, ctx) -> ctx.status(401).json(error(401, ex)))
                .exception(InvalidStationId.class,  (ex, ctx) -> ctx.status(400).json(error(400, ex)));
        // @formatter:on
    }

    private static ErrorResponse error(int status, Exception exception) {
        return new ErrorResponse(status, exception.getMessage());
    }

    public void start(int port) {
        this.webapp.start(port);
    }

    record ErrorResponse(int status, String message, String timestamp) {
        public ErrorResponse(int status, String message) {
            this(status, message, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }
}
